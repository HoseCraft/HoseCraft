# HoseCraft [![Build Status](https://hub.thomas-edwards.me/jenkins/job/HoseCraft/badge/icon)](https://hub.thomas-edwards.me/jenkins/job/HoseCraft)

### Totally not copying Bukkit and Spigots name and style!
### We have a site now! http://hosecraft.github.io/HoseCraft/

## Introduction

You may be wondering what in the hell HoseCraft is, well you're right to do so.  
It's been a while since yet another Minecraft Server software appeared.

However, that's not the case.  
HoseCraft is a server software for Minecraft Classic with the bonus feature of actually being the original Minecraft Classic Server.

You read right, at the core of HoseCraft is the original Minecraft Classic server notch wrote many eons ago. With a lovingly crafted plugin API ontop.  
An API with custom events and command support nonetheless!

## What will we do with HoseCraft and what will we not?

We will be adding more and more to the API to give plugin creators full (yes, full) control over any aspect of the server as they desire. Giving them access to control players and blocks within the world through many different methods and events. We plan to setup an event for almost every thinkable thing.

We WON'T be adding CPE Support (More on that later), nor will we be adding features that impede the original Minecraft Classic experiences. We've removed general block restrictions that kicked players for placing blocks such as Grass, we'll be leaving intact original messages from when players join or leave. As well as how the game plays altogether. This is up to you to modify. You can simply get a copy of HoseCraft and run it as an original classic server, you'd hardly know the difference. Or you could install a buttload of plugins and make your server truly kick-ass!

## Classic Protocol Extension Support (CPE)

As mentioned above we won't be adding CPE support for clients. However this doesn't mean we won't give support for plugin authors to add it. Currently HoseCraft already has some CPE PacketTypes as well as methods to detect if a player's client supports CPE. Without a lot of hacking about adding CPE right now would be either difficult or impossible. But we do plan to change this, in future we plan to allow adding custom block types as well as adding custom packets.

Doing all of the above will allow crafty plugin authors to create plugins that add in CPE support if they so desire it.

## Mmm.. Flavours!

Because its really fancy and what not. Every major version of HoseCraft has a codename. We'll call them flavours. Find below a list of the current and past flavours!

*   v1.x - Vanilla

Each time we hit a major version, which can only be done officially, contributors can't kick up the version number to a new major. We'll reach out to the community/followers on IRC to suggest a new flavour, right now we'd like to try and keep to a naming theme.

## Fun stuff... Whats implemented?

*   Player events for Kick, Op, Deop, Ban and Move
*   Block events for Break and Place
*   Custom world generator support via plugins
*   Custom commands via plugins
*   Custom chat messages via plugins

## Boring stuff... Whats planned?

Due to the project being in its infancy stages (As of writing, about 4-5 days old) theres a lot of stuff we plan to do. There's also a fair bit to get done.

*   Clean up plugin loading code, Really unstable and hacky
*   General housekeeping, clean up code and fix any known bugs.
*   Release version 2.0! Whoop
*   Start taking a pull requests and ideas.

## HoseCraft lives on IRC!

You can ask for help, or contribute to HoseCraft verbally on IRC. We're sat in #HoseCraft on irc.spi.gt

Feel free to drop in and say hello!  
Don't expect a reply instantly though!

## API? API!!

As mentioned a few hundred times above, HoseCraft has a plugin API. For the most part it works very similar to Bukkits API. This doesn't mean you can either load Bukkit/Spigot plugins or write plugins exactly the same.

The API although similar has large differences in how it handles different things, for example commands use a command registry instead of defining them in the plugin.yml and using a method.

The API is ever changing currently and as a result JavaDocs will not be provided. Feel free to generate them yourself from the source. Creating a plugin is very similar to Bukkit in needing a plugin.yml file. Use a server Jar as the library when developing your plugin.

## Maven
Finally, HoseCraft can now be added to your pom file pain free.
You'll be wanting the following lines:

```xml
<repositories>
    <repository>
        <id>nexus</id>
        <url>https://hub.thomas-edwards.me/nexus/content/repositories/snapshots/</url>
    </repository>
</repositories>
<dependencies>
    <dependency>
        <groupId>com.infermc.hosecraft</groupId>
        <artifactId>HoseCraft</artifactId>
        <version>1.2.1-SNAPSHOT</version>
        <scope>provided</scope>
    </dependency>
</dependencies>
```