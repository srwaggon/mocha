# Mocha Readme
Mocha is currently a tile based game template, with simple hitbox collision.

[![CircleCI](https://circleci.com/gh/srwaggon/mocha.svg?style=svg)](https://circleci.com/gh/srwaggon/mocha)
 

## Backlog
- [ ] Remove ChunkRepository access from Grass Grows / Water Evaporates rules

## Bugs
- [x] TileUpdates are not firing (probably because the connection isn't getting put into the mochaConnectionsByPlayerId map currently)
- [x] The player gets a new player ID every time they connect
- [ ] The player's sprite is not centered on the screen. It is in the SE quadrant of the center.
- [ ] The player's sprite is not centered on its entity's hitbox. The right and bottom sides overhang by any difference in size.

## Stories / Epics
These represent units of work **to be done.**

### Accounts
- [ ] Gracefully inform registering player when the account name is already taken
- [ ] Enable client-side specification between registering / logging-in

### Items
- [ ] Chain item prototypes together

### Entities
- [ ] Chain entity prototypes together

### Inventory
- [x] Pickaxe sprite.
- [x] Pickaxes exist in the world.
- [x] Items disappear from the world when picked up.
- [x] A network player can send a command to pickup an item.
- [x] Swords exist in the world.
- [ ] Swords / Pickaxes can be dropped by the player and appear in the world when dropped.
- [ ] A player can open their inventory and view its contents.

### World
- [ ] Tiles can hold things?
- [ ] Bushes exist in the world.
- [ ] Bushes disappear when struck by a sword.
- [ ] Stones disappear when struck by a pickaxe.
- [x] I can save and load maps to disk.
- [x] I can persist entities and tiles to a database for loading.

### Pets
- [ ] There exist wild mobs.

### Graphics
- [x] Tile graphics connect across chunks.
- [x] Graphics are layered (background, mask, foreground, sprites, particles, UI)
- [x] Spike: Can I handle click events directly on graphical components?: Yes.
- [x] Spike: Can I draw directly on the canvas instead of using a writable image? Yes, but it's more performant to use many canvases.

### Sounds
- [x] A sound is played when an item is picked up.
- [ ] A sound is played when a pickaxe is swung.
- [ ] A sound is played when a stone is struck by a pickaxe.
- [ ] A sound is played when a sword is swung.
- [ ] A sound is played when an entity is struck by a sword.

### Networking
- [x] Issue: The client gets mad when it can't find a server: massive blood trail
- [x] There is a game client.
- [x] The game server responds to chunk requests with the requested chunk's data.
- [x] The game client renders a chunk requested from the server.
- [x] The game server responds to entities requests with the requested entities's data.
- [x] The game client renders entities requested from the server.
- [x] The client can distinguish entity updates from additions.
- [x] The game server accepts proposals for moving an entities.
- [x] The game client proposes to move an entities upon key-press.
- [x] Server entity movements are propagated to clients.
- [x] When a client connects, a new entity is created for that client.
- [x] The server associates incoming packets with the sender.
- [x] When a client disconnects, their entity is removed.
- [x] Server sends data to clients beyond the first
- [x] The server forgets a client when it disconnects, and does not continue to send it messages.
- [x] If a player asks for a non-existent entity, server responds with Entity Removed

## Ideas
* Architect by layer
  * Each layer feeds into the next.
  * Input layer
  * Game logic
  * Physics
  * Rendering
* Movement
  * An entity near a corner will be able to slide left/right (relative to the entity's face) and around the corner so that they can continue forward.
* Crafting
  * Have to use correct tool on correct items at proper moment.
  * Using a hammer on stone creates stone chunks.
  * Placing 2x stone chunks on a crafting table with 1x wood planks and hitting with a hammer creates a stone wall.
* Soft gates
  * Hidden skills that improve, instead of levels that represent arbitrary milemarkers.
    * Skills improve with use.
  * Players need to _know_ the recipes to craft them.
  * Players are not limited to classes they select at character creation, rather by where they have invested their efforts.
  * Pivoting requires starting a new tree (but does not destroy previous efforts).
  * Inventory is limited to a 16-slot backpack (and 2 slots for hands), so that players have to carefully select what to carry.

## Notes

