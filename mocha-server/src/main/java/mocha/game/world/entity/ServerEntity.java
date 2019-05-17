package mocha.game.world.entity;


import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Transient;

import mocha.game.world.collision.Collision;
import mocha.game.world.entity.movement.MovementType;

@javax.persistence.Entity
public class ServerEntity extends BaseEntity {

  public ServerEntity() {
  }

  ServerEntity(Entity entity) {
    this.setId(entity.getId());
    getLocation().set(entity.getLocation());
    this.setBlocking(entity.isBlocking());
    this.setEntityType(entity.getEntityType());
    this.setTypeId(entity.getTypeId());
  }

  @Id
  @Override
  public Integer getId() {
    return super.getId();
  }

  @Column
  public int getX() {
    return getLocation().getX();
  }

  public void setX(int x) {
    getLocation().setX(x);
  }

  @Column
  public int getY() {
    return getLocation().getY();
  }

  public void setY(int y) {
    getLocation().setY(y);
  }

  @Enumerated(EnumType.STRING)
  @Override
  public EntityType getEntityType() {
    return super.getEntityType();
  }

  @Override
  public void setEntityType(EntityType entityType) {
    super.setEntityType(entityType);
  }

  @Column
  @Override
  public int getTypeId() {
    return super.getTypeId();
  }

  @Override
  public void setTypeId(int typeId) {
    super.setTypeId(typeId);
  }

  @Override
  public boolean isBlocking() {
    return super.isBlocking();
  }

  @Column
  @Override
  public void setBlocking(boolean isBlocking) {
    super.setBlocking(isBlocking);
  }

  @Enumerated(EnumType.STRING)
  @Override
  public MovementType getMovementType() {
    return MovementType.SLIDING;
  }

  @Override
  public void setMovementType(MovementType movementType) {
    super.setMovementType(movementType);
  }

  @Override
  public int getSpeed() {
    return super.getSpeed();
  }

  @Column
  @Override
  public int getWidth() {
    return super.getWidth();
  }

  @Column
  @Override
  public int getHeight() {
    return super.getHeight();
  }


  @Override
  public void setCollision(Collision collision) {
    super.setCollision(collision);
  }

  @Transient
  @Override
  public Collision getCollision() {
    return super.getCollision();
  }
}
