package org.kwicket.sample.async

import org.apache.wicket.model.IModel
import org.kwicket.component.q
import org.kwicket.model.res
import org.kwicket.sample.shared.page.SampleBasePage
import org.kwicket.wicket.core.markup.html.basic.KLabel
import org.kwicket.wicket.core.markup.html.link.KBookmarkablePageLink


class AsyncHomePage : SampleBasePage() {

    init {
        q(KLabel(id = "asyncModel", model = "Async Model".res()))
        q(KLabel(id = "serialModelLabel", model = "Serial Model Page".res()))
        q(KLabel(id = "parallelModelLabel", model = "Parallel Model Page".res()))
        q(
            KBookmarkablePageLink(
                id = "serialModelPageLink",
                page = AsyncModelPage::class,
                params = AsyncModelPage.makeParams(parallel = false)
            )
        )
        q(
            KBookmarkablePageLink(
                id = "parallelModelPageLink",
                page = AsyncModelPage::class,
                params = AsyncModelPage.makeParams(parallel = true)
            )
        )

        q(KLabel(id = "asyncTable", model = "Async Table".res()))
        q(KLabel(id = "serialTableLabel", model = "Serial Table Page".res()))
        q(KLabel(id = "parallelTableLabel", model = "Parallel Table Page".res()))
        q(
            KBookmarkablePageLink(
                id = "serialTablePageLink",
                page = AsyncTablePage::class,
                params = AsyncTablePage.makeParams(parallel = false)
            )
        )
        q(
            KBookmarkablePageLink(
                id = "parallelTablePageLink",
                page = AsyncTablePage::class,
                params = AsyncTablePage.makeParams(parallel = true)
            )
        )
    }

    override val pageTitleModel: IModel<String>
        get() = "Async Home Page".res()

}