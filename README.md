# IndividualJoinMessages
Customise join/leave messages for each player individually

Developed for the University of Alberta Minecraft Club, available for public use

## Usage
- Download Individual_Join_Messages_1.20.jar above
- This plugin was tested on Paper 1.20.1. It should work on any 1.20 version.
- Install the plugin
- Run the server to generate config files and initialize database
- Use in-game or server console commands to set players' messages (see below)
- Use the config file to cusomize role colors

## Commands
/joinmessage set [username] [message]
 - The message can contain spaces as normal
 - Type the entire message, including the player's username (technically optional)
 - The player's username will be colored with their role color (if found exactly as it appears)
 - Supports Bukkit color codes with \& for §

/joinmessage remove [username]
 - removes a user's message

/joinmessage get [username]
 - displays the message to the command sender (not global)

/joinmessage list
 - list of all usernames with a custom message registered

/joinmessage reload
 - reloads the config
 - only needed when changing role colors, NOT customising messages

To customize leave messages, use /leavemessage instead
 - /joinmessage reload and /leavemessage reload do the same thing


### Notes
 - A change in username will require re-doing the custom message
 - Message will be yellow by default, but the color of the entire message or any part can be changed with Bukkit color codes
