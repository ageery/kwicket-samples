I18N App
========

Overview
--------

Example of internationalization using a `.properties.xml` file and the kWicket
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
native phrases themselves; there is no need to devise a key naming convention
such as would be required with a `.properties` file (which does not allow
spaces in the keys).

In the example, the English localization does _not_ specify "Name" or 
"Language" (so these labels are rendered as-is) but _does_ specify "Job Title". 
The Chinese localization specifies all of the resources.

When the language drop-down is changed, the container is refreshed via ajax
and the labels for the specified language are displayed properly.
