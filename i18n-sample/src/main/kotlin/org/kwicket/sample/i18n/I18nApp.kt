package org.kwicket.sample.i18n

import org.kwicket.sample.shared.app.AbstractSampleBootApp
import org.kwicket.sample.shared.app.SampleWicketApp
import org.springframework.boot.SpringApplication

class I18nWebApp : SampleWicketApp(homePage = I18nPage::class.java)

class I18nApp : AbstractSampleBootApp(wicketApp = I18nWebApp())

fun main(args: Array<String>) {
    SpringApplication.run(I18nApp::class.java, *args)
}