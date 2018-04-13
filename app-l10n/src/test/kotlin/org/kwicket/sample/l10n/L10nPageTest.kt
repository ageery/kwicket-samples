package org.kwicket.sample.l10n

import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior
import org.apache.wicket.markup.html.form.DropDownChoice
import org.apache.wicket.util.tester.WicketTester
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import java.util.*

object L10nPageTest : Spek({

    val contextPath = "html"
    val containerPath = "$contextPath:container"
    val jobLabelPath = "$containerPath:jobLabel"
    val nameLabelPath = "$containerPath:nameLabel"
    val namePath = "$containerPath:name"
    val formPath = "$contextPath:form"
    val localeSelectorId = "locale"
    val localePath = "$formPath:$localeSelectorId"

    val englishJobLabel = "Occupation"
    val chineseJobLabel = "工作"

   given("the l10n application") {
       val tester = WicketTester(L10nWebApp())

       on("loading the home page with an English-language locale") {
           tester.session.locale = Locale.ENGLISH
           tester.startPage(L10nPage::class.java)
           it("should render") {
               tester.assertRenderedPage(L10nPage::class.java)
           }
           it("should have a label with 'Name' (coming from the literal in code)") {
               tester.assertLabel(nameLabelPath, "Name")
           }
           it("should have a label with 'Donald Duck' (coming from the model)") {
               tester.assertLabel(namePath, "Donald Duck")
           }
           it("should have a label with '$englishJobLabel' (coming from the properties.xml file)") {
               tester.assertLabel(jobLabelPath, englishJobLabel)
           }
       }

       on("loading the home page with a French-language locale") {
           tester.session.locale = Locale.FRENCH
           tester.startPage(L10nPage::class.java)
           it("should render") {
               tester.assertRenderedPage(L10nPage::class.java)
           }
           it("should have a label with 'Name' (coming from the literal in code)") {
               tester.assertLabel(nameLabelPath, "Name")
           }
           it("should have a label with 'Donald Duck' (coming from the model)") {
               tester.assertLabel(namePath, "Donald Duck")
           }
           it("should have a label with '$englishJobLabel' (coming from the properties.xml file)") {
               tester.assertLabel(jobLabelPath, englishJobLabel)
           }
       }

       on("selecting Chinese as the language when in an English locale") {
           tester.session.locale = Locale.ENGLISH
           tester.startPage(L10nPage::class.java)
           it("should change the '$englishJobLabel' label to '$chineseJobLabel'") {
               val formTester = tester.newFormTester(formPath, false)
               val localeSelector = tester.getComponentFromLastRenderedPage(localePath) as DropDownChoice<Locale>
               val index = localeSelector.choices.indexOf(Locale.SIMPLIFIED_CHINESE)
               formTester.select(localeSelectorId, index)
               val locale = tester.getComponentFromLastRenderedPage(localePath)
               val behavior= locale.getBehaviors(AjaxFormComponentUpdatingBehavior::class.java)[0]
               tester.executeBehavior(behavior)
               tester.assertComponentOnAjaxResponse(containerPath)
               tester.assertLabel(jobLabelPath, chineseJobLabel)
           }
       }

       on("loading the home page with a Simplified Chinese locale") {
           tester.session.locale = Locale.SIMPLIFIED_CHINESE
           tester.startPage(L10nPage::class.java)
           it("should render") {
               tester.assertRenderedPage(L10nPage::class.java)
           }
           it("should have a label with '$chineseJobLabel'") {
               tester.assertLabel(jobLabelPath, chineseJobLabel)
           }
       }

       on("selecting English as the language when in a Chinese locale") {
           tester.session.locale = Locale.SIMPLIFIED_CHINESE
           tester.startPage(L10nPage::class.java)
           it("should change the '$chineseJobLabel' label to '$englishJobLabel' when selecting English from the locale selector") {
               val formTester = tester.newFormTester(formPath, false)
               val localeSelector = tester.getComponentFromLastRenderedPage(localePath) as DropDownChoice<Locale>
               val index = localeSelector.choices.indexOf(Locale.ENGLISH)
               formTester.select(localeSelectorId, index)
               val locale = tester.getComponentFromLastRenderedPage(localePath)
               val behavior= locale.getBehaviors(AjaxFormComponentUpdatingBehavior::class.java)[0]
               tester.executeBehavior(behavior)
               tester.assertComponentOnAjaxResponse(containerPath)
               tester.assertLabel(jobLabelPath, englishJobLabel)
           }
       }
   }

})