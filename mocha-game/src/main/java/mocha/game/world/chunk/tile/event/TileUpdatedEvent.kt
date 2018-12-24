package mocha.game.world.chunk.tile.event

import mocha.game.world.chunk.Chunk

data class TileUpdatedEvent(val chunk: Chunk, val x: Int, val y: Int)
