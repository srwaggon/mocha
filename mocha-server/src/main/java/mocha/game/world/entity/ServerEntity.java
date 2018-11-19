package mocha.game.world.entity;


import javax.persistence.Id;

@javax.persistence.Entity
public class ServerEntity extends Entity {

  @Id
  private Integer id;
}
