# Creating an OpMode

To create an OpMode in NextFTC, all you need to do is create a class that extends `NextFTCOpMode`!

You now have access to a bunch of useful functionality that isn't natively shipped in the SDK. Let's
go through it.

## User-overridable functions

You will be interacting with these a lot when creating OpModes. The functions are `onInit()`, 
`onWaitForStart()`, `onStartButtonPressed()`, `onUpdate()`, and `onStop()`.

## Gamepad functionality

NextFTC offers a feature where you can map `Commands` to specific buttons, joysticks, and triggers
on your Gamepads. NextFTC handles the tricky parts of ensuring they are updated, values are checked,
and that everything happens in parallel with the CommandManager. All you need to do is define your
gamepad mappings in your `onInit()` function..

## Pedro Path following
