package mocha.game.world.item

import mocha.shared.Identified

open class Item : Identified<Int> {

  private var id: Int? = null
  open var itemPrototype: ItemPrototype? = null
  open var data0: Int? = null
  open var data1: Int? = null
  open var data2: Int? = null

  val spriteId: String?
    get() = itemPrototype!!.spriteId

  constructor()

  constructor(id: Int?, itemPrototype: ItemPrototype, data0: Int?, data1: Int?, data2: Int?) {
    this.id = id
    this.itemPrototype = itemPrototype
    this.data0 = data0
    this.data1 = data1
    this.data2 = data2
  }

  override fun getId(): Int? {
    return id
  }

  override fun setId(id: Int?) {
    this.id = id
  }
}
