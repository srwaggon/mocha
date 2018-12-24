package mocha.game.world.item

import mocha.game.world.item.itemprototype.ItemPrototype

data class UpdateItemCommand(
    val id: Int,
    val itemPrototype: ItemPrototype,
    val data0: Int,
    val data1: Int,
    val data2: Int
)
