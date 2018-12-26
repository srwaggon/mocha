package mocha.game.world.entity.prototype;

import mocha.game.world.entity.EntityType;
import mocha.net.packet.AbstractPacket;
import mocha.net.packet.PacketType;

public class EntityPrototypeUpdatePacket extends AbstractPacket {

  public EntityPrototypeUpdatePacket() {
  }

  public EntityPrototypeUpdatePacket(EntityPrototype entityPrototype) {
    addToData(entityPrototype.getId());
    addToData(entityPrototype.getBaseEntityPrototypeId());
    addToData(entityPrototype.getEntityType().toString());
    addToData(entityPrototype.getTypeId());
    addToData(entityPrototype.isBlocking());
    addToData(entityPrototype.getSpriteId());
    addToData(entityPrototype.getScale());
  }

  @Override
  public PacketType getType() {
    return PacketType.ENTITY_PROTOTYPE_UPDATE;
  }

  public int getId() {
    return getDataAsInt(1);
  }

  public int getBaseEntityPrototypeId() {
    return getDataAsInt(2);
  }

  public EntityType getEntityType() {
    return EntityType.valueOf(getData(3));
  }

  public int getTypeId() {
    return getDataAsInt(4);
  }

  public boolean getIsBlocking() {
    return getDataAsBoolean(5);
  }

  public String getSpriteId() {
    return getData(6);
  }

  public double getScale() {
    return getDataAsDouble(7);
  }

  public EntityPrototype getEntityPrototype() {
    return new EntityPrototype(
        getId(),
        getBaseEntityPrototypeId(),
        getEntityType(),
        getTypeId(),
        getIsBlocking(),
        null,
        null,
        getSpriteId(),
        getScale()
    );
  }
}
