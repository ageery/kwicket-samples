package org.kwicket.sample.async

import org.kwicket.sample.shared.app.AbstractSampleBootApp
import org.kwicket.sample.shared.app.SampleWicketApp
import org.springframework.boot.SpringApplication

class AsyncWebApp : SampleWicketApp(homePage = AsyncHomePage::class.java)

class AsyncApp : AbstractSampleBootApp(wicketApp = AsyncWebApp())

fun main(args: Array<String>) {
    SpringApplication.run(AsyncApp::class.java, *args)
}