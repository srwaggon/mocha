# Mocha Readme
Mocha is currently a tile based game template, with simple hitbox collision.
 

# Backlog
## Bugs
1. The player's sprite is not centered on the screen. It is in the SE quadrant of the center.
1. The player's sprite is not centered on its entity's hitbox. The right and bottom sides overhang by any difference in size.

## Stories / Epics
These represent units of work **to be done.**

### Inventory
[x] Pickaxe sprite.
[x] Pickaxes exist in the world.
[x] Swords / Pickaxes can be picked up and disappear from the world when picked up.
1. Swords exist in the world.
1. Swords / Pickaxes can be dropped by the player and appear in the world when dropped.
1. A player can open their inventory and view its contents.

### World
[x] Tiles have inventory instead of items.
1. Bushes exist in the world.
1. Bushes disappear when struck by a sword.
1. Stones disappear when struck by a pickaxe.
1. I can save and load maps to disk.
1. I can persist entities and tiles to a database for loading.

### Pets
1. There exist wild mobs.

### Graphics
1. Tile graphics connect across chunks.
1. Graphics are layered (background, mask, foreground, sprites, particles, UI)
1. Spike: Can I handle click events directly on graphical components? 
1. Spike: Can I draw directly on the canvas instead of using a writable image?

### Sounds
[x] A sound is played when an item is picked up.
1. A sound is played when a pickaxe is swung.
1. A sound is played when a stone is struck by a pickaxe.
1. A sound is played when a sword is swung.
1. A sound is played when an entity is struck by a sword.

### Networking
[x] There is a game client.
[x] The game server responds to chunk requests with the requested chunk's data.
[x] The game client renders a chunk requested from the server.
[x] The game server responds to entities requests with the requested entities's data.
[x] The game client renders entities requested from the server.
[x] The client can distinguish entity updates from additions.
1. The game server accepts proposals for moving an entities.
1. The game client proposes to move an entities upon key-press.

# Ideas
+ Architect by layer
  + Each layer feeds into the next.
  + Input layer
  + Game logic
  + Physics
  + Rendering
+ Movement
  + An entity near a corner will be able to slide left/right (relative to the entity's face) and around the corner so that they can continue forward.
+ Crafting
  + Have to use correct tool on correct items at proper moment.
  + Using a hammer on stone creates stone chunks.
  + Placing 2x stone chunks on a crafting table with 1x wood planks and hitting with a hammer creates a stone wall.
+ Soft gates
  + Hidden skills that improve, instead of levels that represent arbitrary milemarkers.
    + Skills improve with use.
  + Players need to _know_ the recipes to craft them.
  + Players are not limited to classes they select at character creation, rather by where they have invested their efforts.
  + Pivoting requires starting a new tree (but does not destroy previous efforts).
  + Inventory is limited to a 16-slot backpack (and 2 slots for hands), so that players have to carefully select what to carry.

# Notes

