package org.kwicket.sample.async

import kotlinx.coroutines.experimental.delay
import org.apache.wicket.model.IModel
import org.apache.wicket.request.mapper.parameter.PageParameters
import org.joda.time.LocalTime.now
import org.kwicket.behavior.AsyncModelLoadBehavior
import org.kwicket.component.q
import org.kwicket.model.ldm
import org.kwicket.model.res
import org.kwicket.sample.shared.page.SampleBasePage
import org.kwicket.wicket.core.markup.html.basic.KLabel
import org.wicketstuff.annotation.mount.MountPath
import java.util.concurrent.TimeUnit.SECONDS

@MountPath("async")
class AsyncPage(params: PageParameters) : SampleBasePage(params = params) {

    companion object {
        const val delayAmt = 3L
        val delayedTime = suspend {
            delay(time = delayAmt, unit = SECONDS)
            now()
        }
    }

    init {
        val parallel = params["p"].toBoolean(false)
        val createBehaviors = { if (parallel) arrayOf(AsyncModelLoadBehavior()) else emptyArray() }
        q(
            KLabel(
                id = "overview", model = {
                    """The parallel mode is '$parallel' so the two labels should be
                    ${if (parallel) "at approximately the same time" else "approximately $delayAmt seconds apart"}"""
                }.ldm()
            )
        )
        q(KLabel(id = "time1Label", model = "1st label rendered at".res()))
        q(KLabel(id = "time1", model = suspend { delayedTime() }.ldm(), behaviors = *createBehaviors.invoke()))
        q(KLabel(id = "time2Label", model = "2nd Label rendered at".res()))
        q(KLabel(id = "time2", model = suspend { delayedTime() }.ldm(), behaviors = *createBehaviors.invoke()))
    }

    override val pageTitleModel: IModel<String>
        get() = "Async Page".res()

}