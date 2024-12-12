# Command system notes
It is inefficient to constantly create new Command objects every time we want to add them. It's fine for now, but 
in the future see if it's possible to have commands persist between runs of them.

Note that findConflicts() only returns a single command. This is because it is called for every attempt
at adding a command. It is therefore impossible for there to be two commands that both have a subsystem running
at the same time.

# Note
The `core` and `ftc` packages should be implemented as modules in the final library