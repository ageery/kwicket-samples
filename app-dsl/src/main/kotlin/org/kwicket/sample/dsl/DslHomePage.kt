package org.kwicket.sample.dsl

import kotlinx.html.span
import org.apache.wicket.model.IModel
import org.kwicket.component.q
import org.kwicket.kotlinx.html.RegionInfoPanel
import org.kwicket.kotlinx.html.div
import org.kwicket.kotlinx.html.panel
import org.kwicket.kotlinx.html.region
import org.kwicket.kotlinx.html.span
import org.kwicket.model.ldm
import org.kwicket.model.model
import org.kwicket.model.res
import org.kwicket.sample.shared.page.SampleBasePage
import org.kwicket.wicket.core.markup.html.KWebMarkupContainer
import org.kwicket.wicket.core.markup.html.basic.KLabel
import java.time.LocalDateTime


class DslHomePage : SampleBasePage() {

    init {
        q(RegionInfoPanel(id = "panel", model = "Sunny".model(), region = ::weather))
    }

    override val pageTitleModel: IModel<String>
        get() = "DSL Home Page".res()

    private fun weather(model: IModel<String>) = region().panel {
        div(builder = { KWebMarkupContainer(id = it) }) {
            span { +"The weather today at " }
            span(builder = { KLabel(id = it, model = { LocalDateTime.now().toLocalTime() }.ldm()) })
            span { +" is " }
            span(model = model)
        }
    }

}