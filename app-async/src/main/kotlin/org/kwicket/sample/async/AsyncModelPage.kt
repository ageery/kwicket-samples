package org.kwicket.sample.async

import org.apache.wicket.model.IModel
import org.apache.wicket.request.mapper.parameter.PageParameters
import org.joda.time.LocalTime.now
import org.kwicket.behavior.AsyncModelLoadBehavior
import org.kwicket.component.q
import org.kwicket.model.ldm
import org.kwicket.model.res
import org.kwicket.model.sldm
import org.kwicket.sample.shared.page.SampleBasePage
import org.kwicket.toParams
import org.kwicket.wicket.core.markup.html.basic.KLabel
import org.wicketstuff.annotation.mount.MountPath
import java.lang.Thread.sleep

@MountPath("model/async")
class AsyncModelPage(params: PageParameters) : SampleBasePage(params = params) {

    companion object {
        private const val delayAmt = 3
        private const val parallelParamName = "p"
        fun makeParams(parallel: Boolean) = (parallelParamName to parallel).toParams()
    }

    init {
        val parallel = params[parallelParamName].toBoolean(false)
        if (parallel) add(AsyncModelLoadBehavior())

        q(
            KLabel(
                id = "overview", model = {
                    """The parallel mode is '$parallel' so the two labels should be
                    ${if (parallel) "at approximately the same time" else "approximately $delayAmt seconds apart"}"""
                }.ldm()
            )
        )
        q(KLabel(id = "time1Label", model = "1st label rendered at".res()))
        q(KLabel(id = "time1", model = { sleep(delayAmt * 1000L); now()}.sldm()))
        q(KLabel(id = "time2Label", model = "2nd Label rendered at".res()))
        q(KLabel(id = "time2", model = { sleep(delayAmt * 1000L); now()}.sldm()))
    }

    override val pageTitleModel: IModel<String>
        get() = "Async Page".res()

}