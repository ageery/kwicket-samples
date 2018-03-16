plugins {
    id("org.springframework.boot")
    id("org.jetbrains.kotlin.jvm")
    id("org.jetbrains.kotlin.plugin.spring")
    id("io.spring.dependency-management")
}

val wicketVersion by project
val wicketStuffVersion by project
val wicketBootstrapVersion by project
val kWicketVersion by project
val kotlinxHtmlVersion by project

dependencies {
    compile("org.springframework.boot:spring-boot-starter-web")

    compile("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    compile("org.jetbrains.kotlin:kotlin-reflect")

    compile("org.jetbrains.kotlinx:kotlinx-html-jvm:${ext["kotlinxHtmlVersion"]}")
    compile("org.kwicket:kwicket-kotlinx-html:$kWicketVersion")

    compile("org.apache.wicket:wicket-core:$wicketVersion")
    compile("org.kwicket:kwicket-wicket-core:$kWicketVersion")

    compile("org.apache.wicket:wicket-extensions:$wicketVersion")
    compile("org.kwicket:kwicket-wicket-extensions:$kWicketVersion")

    compile("org.apache.wicket:wicket-spring:$wicketVersion")
    compile("org.kwicket:kwicket-wicket-spring:$kWicketVersion")

    compile("org.wicketstuff:wicketstuff-annotation:$wicketStuffVersion")
    compile("org.kwicket:kwicket-wicketstuff-annotation:$kWicketVersion")

    compile("org.wicketstuff:wicketstuff-select2:$wicketStuffVersion")
    compile("org.kwicket:kwicket-wicketstuff-select2:$kWicketVersion")

    compile("de.agilecoders.wicket:wicket-bootstrap-core:$wicketBootstrapVersion")
    compile("org.kwicket:kwicket-wicket-bootstrap-core:$kWicketVersion")

    compile("de.agilecoders.wicket:wicket-bootstrap-extensions:$wicketBootstrapVersion")
    compile("de.agilecoders.wicket:wicket-bootstrap-themes:$wicketBootstrapVersion")

}