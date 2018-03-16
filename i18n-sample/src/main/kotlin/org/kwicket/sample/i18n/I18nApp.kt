package org.kwicket.sample.i18n

import org.kwicket.sample.shared.app.AbstractSampleApp
import org.kwicket.wicket.core.protocol.http.KWicketFilter
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
class I18nApp {
    @Bean
    fun getWicketFilter() = KWicketFilter(webApp = I18nWebApp(), filterPath = "/")
}

class I18nWebApp : AbstractSampleApp() {
    override fun getHomePage() = I18nPage::class.java
}

fun main(args: Array<String>) {
    SpringApplication.run(I18nApp::class.java, *args)
}