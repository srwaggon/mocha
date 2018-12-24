package mocha.game.world.item.itemprototype

import mocha.game.world.item.ItemType
import mocha.shared.Identified

open class ItemPrototype : Identified<Int> {

  private var id: Int? = null
  open var name: String? = null
  open var spriteId: String? = null
  open var itemType: ItemType? = null
  open var description: String? = null

  constructor()

  constructor(id: Int?, name: String, spriteId: String, itemType: ItemType, description: String) {
    this.id = id
    this.name = name
    this.spriteId = spriteId
    this.itemType = itemType
    this.description = description
  }

  override fun getId(): Int? {
    return id
  }

  override fun setId(id: Int?) {
    this.id = id
  }
}
