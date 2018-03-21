package org.kwicket.sample.l10n

import de.agilecoders.wicket.core.markup.html.bootstrap.image.GlyphIconType
import de.agilecoders.wicket.core.markup.html.bootstrap.navbar.Navbar
import de.agilecoders.wicket.core.markup.html.bootstrap.navbar.NavbarComponents
import org.apache.wicket.behavior.Behavior
import org.apache.wicket.event.Broadcast
import org.kwicket.agilecoders.wicket.core.ajax.markup.html.bootstrap.navbar.KNavbar
import org.kwicket.agilecoders.wicket.core.ajax.markup.html.bootstrap.navbar.KNavbarAjaxLink
import org.kwicket.agilecoders.wicket.core.ajax.markup.html.bootstrap.navbar.KNavbarButton
import org.kwicket.agilecoders.wicket.extensions.markup.html.bootstrap.form.select.KBootstrapSelect
import org.kwicket.behavior.onEvent
import org.kwicket.component.q
import org.kwicket.component.refresh
import org.kwicket.model.ldm
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

/*
 * Event object indicating that a component should be updated via ajax.
 */
object AjaxRefresh

/*
 * Returns a behavior to refresh a component when the `AjaxRefresh` event payload is received.
 */
private fun ajaxRefreshHandler(): Behavior = onEvent<AjaxRefresh>(outputMarkupId = true) { _, component ->
    component.refresh()
}

class L10nPage : SampleBasePage() {

    init {

        /*
         * Data container.
         */
        val model = Person(name = "Donald Duck", job = "Cartoon").model()
        q(KWebMarkupContainer(id = "container", behaviors = *arrayOf(ajaxRefreshHandler())))
        q(KLabel(id = "nameLabel", model = "Name".res()))
        q(KLabel(id = "name", model = model + { it.name }))
        q(KLabel(id = "jobLabel", model = "Job Title".res()))
        q(KLabel(id = "job", model = model + { it.job }))

        /*
         * Language form.
         */
        val localeModel = { session.locale }.ldm()
        q(KForm(id = "form", model = localeModel))
        q(
            KLabel(
                id = "language",
                model = "Language".res(),
                behaviors = *arrayOf(ajaxRefreshHandler())
            )
        )
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
                            send(page, Broadcast.BREADTH, AjaxRefresh)
                        })
                )
            )
        )
    }

    /*
     * Customize the navbar.
     */
    override fun createNavbar(id: String): KNavbar {
        val navbar = super.createNavbar(id)
        navbar.addComponents(
            NavbarComponents.transform(
                Navbar.ComponentPosition.RIGHT,
                KNavbarButton(
                    pageClass = L10nPage::class.java,
                    label = "Home".res(),
                    icon = GlyphIconType.home,
                    behaviors = *arrayOf(ajaxRefreshHandler())
                )
            ) +
                    NavbarComponents.transform(Navbar.ComponentPosition.LEFT,
                        KNavbarAjaxLink(label = "Switch Language".res(),
                            behaviors = *arrayOf(ajaxRefreshHandler()),
                            model = {
                                if (session.locale.language != SIMPLIFIED_CHINESE.language) SIMPLIFIED_CHINESE else ENGLISH
                            }.ldm(),
                            onClick = { _, link ->
                                session.locale = link.model.value
                                setResponsePage(L10nPage::class.java)
                            }
                        )
                    )
        )
        return navbar
    }

}