Async Model Sample
==================

Overview
--------

Example of using Kotlin co-routines for creating Wicket models that are loaded
asynchronously.

Details
-------

Kotlin has built-in support for running asynchronous code. kWicket makes it easy to
use this functionality in Wicket models by providing the `suspend () -> T).ldm()` extension method
for wrapping the receiver in a `LoadableDetachableModel` and the `AsyncModelLoadBehavior` 
for evaluating the model values asynchronously.

In the AsyncPage class, the `delayedTime` `suspend`'able lambda waits three seconds before returning the current time.

The page itself has two labels, each of which use the `delayedTime` lambda for its value. 

The page accepts a parameter, `p`, which determines whether to apply the `AsyncModelLoadBehavior` to each label.

If the value of the parameter `p` is true, the `AsyncModelLoadBehavior` is added to the labels and the page renders
is about three seconds and the two labels have approximately the same time.

If the value of the parameter `p` is false, the `AsyncModelLoadBehavior` is _not_ added to the labels
and the page renders in about six second and the second label has a time that is approximately three seconds
after the first label.

Running
-------

From the _parent_ project:

`./gradlew :async-model-sample:bootRun`

or

`.\gradlew.bat :async-model-sample:bootRun`
