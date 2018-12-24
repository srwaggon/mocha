package mocha.game.world.item.command

import mocha.game.world.item.ItemType

data class UpdateItemPrototypeCommand(
        val id: Int,
        val name: String,
        val spriteId: String,
        val itemType: ItemType,
        val description: String
)
