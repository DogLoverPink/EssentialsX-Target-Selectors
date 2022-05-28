# EssentialsX-Target-Selectors
This minecraft plugin adds @a, @p, and @r to EssentialsX Commands! Works with command block and console aswell!

# Usage:
Just pop this into your server, and it will just work! No commands, no config no nothing! As simple as could be.

# How is this helpful and what does it do?
The very popular plugin [EssentialsX](https://essentialsx.net), has never had support for target selectors like @a, or @p. And especially when working with command blocks, those selectors would be very helpful if they existed, But with this plugin, they do!

@a - all players
@p - closest player
@r - random player
@s - yourself

**Note**: @p and @s can NOT be run from the server console, and @s can NOT be used in command blocks.

**Examples**:
Without plugin (normal essentialsX)

`/give @a dirt` -> `Unknown player @a`

`/kill @p` -> `Unknown player @p`

`give @r diamond 5` - > `Unknown player @r`

With Essentials Selectors Plugin:

`/give @a dirt` -> `*gives every player on the server dirt*`

`/kill @p` -> `*kills the closest player*`

`give @r diamond 5` - > `*gives a random player 5 diamonds*`

There are also specifications for selectors, for example: "@r[gamemode=creative]"

There are 5 specifications that can be added to any selectors:
 - gamemode
 - tag
 - limit
 - distance
 - level
You can also add a "!" before a value to do the opposite. For example: "@<!-- -->A[tag=!"CoolTag"]"

For a list of what all of these do, and how to use them, refer to my spigot page documentation [HERE](https://www.spigotmc.org/resources/essentialsx-selectors-a-p-r-addon.101057/field?field=documentation).

# IMPORTANT:
**This plugin does not replace EssentialsX! You still need to install the orginial [EssentialsX](https://essentialsx.net) plugin for this to work!**


