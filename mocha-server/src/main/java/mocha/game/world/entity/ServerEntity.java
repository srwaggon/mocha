package mocha.game.world.entity;


import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@javax.persistence.Entity
public class ServerEntity extends Entity {

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
  @GeneratedValue(strategy = GenerationType.AUTO)
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
  public EntityType getEntityType() {
    return super.getEntityType();
  }

  public void setEntityType(EntityType entityType) {
    super.setEntityType(entityType);
  }

  @Column
  public int getTypeId() {
    return super.getTypeId();
  }

  public void setTypeId(int typeId) {
    super.setTypeId(typeId);
  }

}
