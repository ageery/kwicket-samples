package org.kwicket.sample.shared.app

import de.agilecoders.wicket.core.markup.html.themes.bootstrap.BootstrapTheme
import de.agilecoders.wicket.core.settings.SingleThemeProvider
import org.apache.wicket.RuntimeConfigurationType
import org.kwicket.agilecoders.enableBootstrap
import org.kwicket.wicket.core.protocol.http.KWebApplication
import org.kwicket.wicket.spring.enableSpringIoC
import org.kwicket.wicketstuff.annotation.enableMountAnnotations

abstract class AbstractSampleApp(type: RuntimeConfigurationType = RuntimeConfigurationType.DEVELOPMENT) :
    KWebApplication(configurationType = type) {

    override fun init() {
        super.init()
        enableMountAnnotations(scanPackages = listOf("org.kwicket.sample"))
        enableBootstrap(themeProvider = SingleThemeProvider(BootstrapTheme()))
        enableSpringIoC()
    }

}