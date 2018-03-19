package org.kwicket.sample.async

import org.apache.wicket.model.IModel
import org.apache.wicket.request.mapper.parameter.PageParameters
import org.kwicket.component.q
import org.kwicket.model.res
import org.kwicket.sample.shared.page.SampleBasePage
import org.kwicket.wicket.core.markup.html.basic.KLabel
import org.kwicket.wicket.core.markup.html.link.KBookmarkablePageLink


class AsyncHomePage : SampleBasePage() {

    init {
        q(KLabel(id = "serialLabel", model = "Serial Model Page".res()))
        q(KLabel(id = "parallelLabel", model = "Parallel Model Page".res()))
        q(
            KBookmarkablePageLink(
                id = "serialLink",
                page = AsyncPage::class.java,
                params = PageParameters().add("p", false)
            )
        )
        q(
            KBookmarkablePageLink(
                id = "parallelLink",
                page = AsyncPage::class.java,
                params = PageParameters().add("p", true)
            )
        )
    }

    override val pageTitleModel: IModel<String>
        get() = "Async Home Page".res()

}