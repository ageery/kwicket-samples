package org.kwicket.sample.l10n

import org.kwicket.sample.shared.app.AbstractSampleBootApp
import org.kwicket.sample.shared.app.SampleWicketApp
import org.springframework.boot.SpringApplication

class L10nWebApp : SampleWicketApp(homePage = L10nPage::class.java)

class L10nApp : AbstractSampleBootApp(wicketApp = L10nWebApp())

fun main(args: Array<String>) {
    SpringApplication.run(L10nApp::class.java, *args)
}