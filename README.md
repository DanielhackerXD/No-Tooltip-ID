# No Tooltip ID — Forge 1.12.2 (client-side only)

Hides the `#0276` / `#0351/3` that vanilla appends to the item name when advanced tooltips
(F3 + H) are on. Everything else in the advanced tooltip (durability, registry name, NBT...) stays.

Fully client-side: no mixins, no coremod, no extra dependencies. Works on any server.

## Build (JDK 8)
    gradlew build
Jar: `build/libs/NoTooltipId-1.12.2-1.0.0.jar` -> drop in the client `mods` folder.

## How it works
`ItemStack#getTooltip` appends ` (#0001)` or ` (#0001/3)` to line 0. Forge then fires
`ItemTooltipEvent` (client only); `TooltipHandler` removes that exact suffix, with a regex
fallback for the case another mod altered the item/damage.

Not compiled/tested in-game by the author of this package.
