package mocha.game.world.entity.prototype

import mocha.game.command.Command

data class UpdateEntityPrototypeCommand(val entityPrototype: EntityPrototype) : Command
