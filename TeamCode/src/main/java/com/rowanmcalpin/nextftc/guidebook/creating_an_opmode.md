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
and that everything happens in parallel with the CommandManager. All you need to do is set up the 
GamepadManager class. There are two ways to do this.

### Method 1: Manually

To use the gamepad functionality in your OpMode, in your `onInit()` function, do the following:

1. Create a GamepadManager object with both gamepads as parameters, and bind it to the 
    `gamepadManager` variable in your class
2. Decide what controls you want to use, and bind each control with an associated command

### Method 2: Automatically

To automatically create the gamepad functionality, in your constructor call in the NextFTCOpMode, set
`autoCreateGamepadManager` to `true`. Now, all you need to do is bind controls with commands in your
`onInit()` function.

## Pedro Path following
