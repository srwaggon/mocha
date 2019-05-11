package mocha.game.world.entity.event

import mocha.game.event.MochaEvent
import mocha.game.world.entity.Entity

data class EntityUpdatedEvent(val entity: Entity) : MochaEvent
