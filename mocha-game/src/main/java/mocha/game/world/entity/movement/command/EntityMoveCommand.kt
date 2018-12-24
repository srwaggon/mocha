package mocha.game.world.entity.movement.command

import mocha.game.command.Command
import mocha.game.world.Direction
import mocha.game.world.Location

data class EntityMoveCommand(
        var entityId: Int,
        val location: Location,
        val direction: Direction,
        val xOffset: Int,
        val yOffset: Int
) : Command
