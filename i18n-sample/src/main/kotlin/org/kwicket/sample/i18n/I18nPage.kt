package org.kwicket.sample.i18n

import org.kwicket.agilecoders.wicket.extensions.markup.html.bootstrap.form.select.KBootstrapSelect
import org.kwicket.component.q
import org.kwicket.component.refresh
import org.kwicket.model.model
import org.kwicket.model.plus
import org.kwicket.model.res
import org.kwicket.model.value
import org.kwicket.sample.shared.page.SampleBasePage
import org.kwicket.wicket.core.ajax.form.KAjaxFormComponentUpdatingBehavior
import org.kwicket.wicket.core.markup.html.KWebMarkupContainer
import org.kwicket.wicket.core.markup.html.basic.KLabel
import org.kwicket.wicket.core.markup.html.form.KChoiceRenderer
import org.kwicket.wicket.core.markup.html.form.KForm
import java.io.Serializable
import java.util.Locale.ENGLISH
import java.util.Locale.SIMPLIFIED_CHINESE

/*
 * Bean backing the page model.
 */
data class Person(val name: String, val job: String) : Serializable

class I18nPage : SampleBasePage() {

    init {

        /*
         * Data container.
         */
        val model = Person(name = "Donald Duck", job = "Cartoon").model()
        val container = q(KWebMarkupContainer(id = "container", outputMarkupId = true))
        q(KLabel(id = "nameLabel", model = "Name".res()))
        q(KLabel(id = "name", model = model + { it.name }))
        q(KLabel(id = "jobLabel", model = "Job Title".res()))
        q(KLabel(id = "job", model = model + { it.job }))

        /*
         * Language form.
         */
        val localeModel = ENGLISH.model()
        q(KForm(id = "form", model = localeModel))
        val languageLabel = q(KLabel(id = "language", model = "Language".res(), outputMarkupId = true))
        q(
            KBootstrapSelect(
                id = "locale",
                model = localeModel,
                nullValid = false,
                choices = listOf(ENGLISH, SIMPLIFIED_CHINESE),
                renderer = KChoiceRenderer(toDisplay = { it.displayName },
                    toIdValue = { locale, _ -> locale.language },
                    toObject = { id, listModel -> listModel.value.first { it.language == id } }),
                behaviors = *arrayOf(
                    KAjaxFormComponentUpdatingBehavior(event = "change",
                        onUpdate = {
                            session.locale = localeModel.value
                            container.refresh()
                            languageLabel.refresh()
                        })
                )
            )
        )
    }

}