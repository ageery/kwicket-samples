kWicket Samples
===============

Overview
--------

Sample Kotlin Wicket applications using Spring Boot.

Details
-------

* The Gradle files are written in Kotlin (`*.gradle.kts` files)
* Common plugins are declared and applied in the root project. 
Individual sample applications need only apply the `org.springframework.boot` plugin.
* The individual sample applications depend on the `sample` module. 
This module includes dependencies used by the individual sample applications. 
It also defines the base page and application that the individual applications extend.