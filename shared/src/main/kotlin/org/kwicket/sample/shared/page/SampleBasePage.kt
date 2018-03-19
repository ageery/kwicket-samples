package org.kwicket.sample.shared.page

import de.agilecoders.wicket.core.markup.html.bootstrap.html.HtmlTag
import de.agilecoders.wicket.core.markup.html.bootstrap.html.IeEdgeMetaTag
import de.agilecoders.wicket.core.markup.html.bootstrap.html.MobileViewportMetaTag
import de.agilecoders.wicket.core.markup.html.bootstrap.image.Icon
import de.agilecoders.wicket.core.markup.html.bootstrap.navbar.Navbar
import de.agilecoders.wicket.extensions.markup.html.bootstrap.icon.FontAwesomeCssReference
import de.agilecoders.wicket.extensions.markup.html.bootstrap.icon.FontAwesomeIconType
import org.apache.wicket.Component
import org.apache.wicket.ajax.IAjaxIndicatorAware
import org.apache.wicket.markup.head.CssReferenceHeaderItem.forReference
import org.apache.wicket.markup.head.IHeaderResponse
import org.apache.wicket.markup.html.WebPage
import org.apache.wicket.markup.html.basic.Label
import org.apache.wicket.request.mapper.parameter.PageParameters
import org.apache.wicket.request.resource.CssResourceReference
import org.kwicket.agilecoders.wicket.core.ajax.markup.html.bootstrap.common.KNotificationPanel
import org.kwicket.agilecoders.wicket.core.ajax.markup.html.bootstrap.navbar.KNavbar
import org.kwicket.component.q
import org.kwicket.model.res

open class SampleBasePage(params: PageParameters? = null) : WebPage(params), IAjaxIndicatorAware {

    companion object {
        private val ajaxIndicatorId = "ajax"
        private val stylePath = "sample-theme.css"
    }

    private val feedback: Component

    protected open val pageTitleModel = "page.title".res()

    init {
        q(HtmlTag("html"))
        q(Label("title", pageTitleModel))
        q(MobileViewportMetaTag("viewport"))
        q(IeEdgeMetaTag("ie-edge"))
        q(
            KNavbar(
                id = "navbar",
                renderBodyOnly = false,
                brandName = "app.name".res(),
                inverted = true,
                position = Navbar.Position.TOP
            )
        )
        q(Icon("ajax-indicator", FontAwesomeIconType.spinner).setMarkupId(ajaxIndicatorId))
        feedback = q(KNotificationPanel(id = "feedback", outputMarkupPlaceholderTag = true))
    }

    override fun renderHead(response: IHeaderResponse) {
        super.renderHead(response)
        response.render(forReference(FontAwesomeCssReference.instance()))
        response.render(forReference(CssResourceReference(SampleBasePage::class.java, stylePath)))
    }

    override fun getAjaxIndicatorMarkupId() = ajaxIndicatorId

}