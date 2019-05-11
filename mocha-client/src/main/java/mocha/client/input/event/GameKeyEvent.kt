package mocha.client.input.event

import mocha.client.input.GameKey

data class GameKeyEvent(val gameKey: GameKey, val isDown: Boolean)
