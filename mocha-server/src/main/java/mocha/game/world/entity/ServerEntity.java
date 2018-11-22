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
    this.id = entity.id;
    this.location.set(entity.getLocation());
  }

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Override
  public int getId() {
    return id;
  }

  @Column
  public int getX() {
    return location.getX();
  }

  public void setX(int x) {
    this.location.setX(x);
  }

  @Column
  public int getY() {
    return location.getY();
  }

  public void setY(int y) {
    this.location.setY(y);
  }
}
