l10N App
========

Overview
--------

Example of localization using a `.properties.xml` file and the kWicket
`String.res()` extension method.

Details
-------

With the `ResourceModel` class, Wicket makes it very easy to localize application 
text like labels.

With the `String.res()` extension method, kWicket makes it even easier to
localize String resources.

The kWicket `String.res()` extension method wraps the receiver in a `ResourceModel`
object and sets the default value, the value to use when no value is found
in a properties file, to the value of the receiver. This enables a "progressive"
localization as there will always be a meaningful value.

When used with a `.properties.xml` file, the localization keys can be the 
native phrases themselves; there is no need to devise a key-naming convention
such as would be required with a `.properties` file (which does not allow
spaces in keys).

For example, in the example, the English localization does _not_ specify "Name" or 
"Language" (so these labels are rendered as-is) but _does_ specify "Job Title". 
The Chinese localization specifies values for all of the key resources.

When the language drop-down is changed, the container is refreshed via ajax
and the labels for the specified language are displayed properly. The refresh
is performed by sending a `AjaxRefresh` event payload and adding the appropriate
`Behavior` to the components to refresh themselves.

The use of `KBootstrapSelect` is also a good example of named parameters: because they're
named it's easy to determine the meaning of each parameter as opposed to relying
on parameter position.

Running
-------

From the _parent_ project:

`./gradlew :app-l10n:bootRun`

or

`.\gradlew.bat :app-l10n:bootRun`
