package mocha.game;

import javax.persistence.Entity;
import javax.persistence.Id;

import mocha.game.player.Player;

@Entity
public class NetworkPlayer implements Player {

  @Id
  private int id;

  public NetworkPlayer(int id) {
    this.id = id;
  }

  @Override
  public Integer getId() {
    return id;
  }

  @Override
  public void setId(Integer id) {
    this.id = id;
  }

}
