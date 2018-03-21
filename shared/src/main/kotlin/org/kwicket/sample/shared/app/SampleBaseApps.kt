package org.kwicket.sample.shared.app

import de.agilecoders.wicket.core.markup.html.themes.bootstrap.BootstrapTheme
import de.agilecoders.wicket.core.settings.SingleThemeProvider
import org.apache.wicket.Page
import org.apache.wicket.RuntimeConfigurationType
import org.apache.wicket.protocol.http.WebApplication
import org.kwicket.agilecoders.enableBootstrap
import org.kwicket.wicket.core.protocol.http.KWebApplication
import org.kwicket.wicket.core.protocol.http.KWicketFilter
import org.kwicket.wicket.spring.enableSpringIoC
import org.kwicket.wicketstuff.annotation.enableMountAnnotations
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean

open class SampleWicketApp(homePage: Class<out Page>,
    type: RuntimeConfigurationType = RuntimeConfigurationType.DEVELOPMENT) :
    KWebApplication(configurationType = type) {

    private val _homePage: Class<out Page> = homePage

    override fun getHomePage(): Class<out Page> = _homePage

    override fun init() {
        super.init()
        enableMountAnnotations(scanPackages = listOf("org.kwicket.sample"))
        enableBootstrap(themeProvider = SingleThemeProvider(BootstrapTheme()))
        //enableSpringIoC()
    }

}

@SpringBootApplication
abstract class AbstractSampleBootApp(val wicketApp: WebApplication) {

    @Bean
    fun getWicketFilter() = KWicketFilter(wicketApp)

}