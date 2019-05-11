package mocha.game.world.entity.command

import mocha.game.command.Command
import mocha.game.world.entity.Entity

data class PickUpItemCommand(val pickingUpEntity: Entity) : Command
