package mocha.game.player.event

import mocha.game.event.MochaEvent
import mocha.game.player.Player

data class PlayerAddedEvent(val player: Player) : MochaEvent
