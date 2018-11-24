package mocha.game.world.entity;


import javax.persistence.Column;
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
}
