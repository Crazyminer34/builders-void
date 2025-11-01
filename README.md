# Builder's Void

This is a simple mod that adds a void dimension for restriction-free building of lawn bases.

> [!CAUTION]
> This mod is currently in Beta, meaning that there may be a significant amount of bugs. Please help us by reporting them using the relevant links!

## Features
- Adds an empty void dimension perfect for building large bases and factories.
- Adds a "Void Pearl" to teleport to and from said dimension.
- Adds a "Linked Void Pearl" that can be bound to a player's base in the void dimension and subsequently used by others to teleport there.
- A 3x3 Obsidian platform is generated at the void dimension teleport location if there's no solid ground present.
- Each player is allocated a unique position in the void dimension to allow for independent bases to be created.
- Record the player's current dimension and position when teleporting to the void and return them there when teleporting back.

## Planned Features
- Add various settings to the void dimension, such as eternal daylight, disabling mob spawning, etc.

## Commands
The mod adds a few commands to assist in debugging and administration. You can see them listed below.

```
/buildersvoid teleport <target>             Teleport to target's base in the void, if it exists.
/buildersvoid teleport <source> <target>    Teleport source to target's base in the void, if it exists.
```

For the commands above, there exist short versions, listed below.

```
/buildersvoid    ->    /bv
 teleport        ->     tp
```

This is to say, instead of, for example, writing `/buildersvoid teleport <target>`, you can write `/bv tp <target>`.

## Credits
_In alphabetical order_

- detpikachu

## Acknowledgements
_In alphabetical order_

- [BlayTheNinth for Balm](https://github.com/TwelveIterationMods/Balm)
- [jaredlll08 for MultiLoader-Template](https://github.com/jaredlll08/MultiLoader-Template)
- [McJty for RFTools Dimensions](https://github.com/McJtyMods/RFToolsDimensions)
- [shedaniel for Cloth Config](https://github.com/shedaniel/cloth-config)
- [Sunekaer for Yeetus Experimentus](https://github.com/nanite/Yeetus-Experimentus)

## License
Copyright (C) 2025 Andrei I. Hava

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <https://www.gnu.org/licenses/>.
