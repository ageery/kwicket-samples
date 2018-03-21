package org.kwicket.sample.dsl

import org.kwicket.sample.shared.app.AbstractSampleBootApp
import org.kwicket.sample.shared.app.SampleWicketApp
import org.springframework.boot.SpringApplication

class DslWebApp : SampleWicketApp(homePage = DslHomePage::class.java)

class DslApp : AbstractSampleBootApp(wicketApp = DslWebApp())

fun main(args: Array<String>) {
    SpringApplication.run(DslApp::class.java, *args)
}