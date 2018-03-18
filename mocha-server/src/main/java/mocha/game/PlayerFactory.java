package mocha.game;

import org.springframework.stereotype.Component;

import javax.inject.Inject;

import mocha.game.world.entity.EntityFactory;
import mocha.net.packet.MochaConnection;

@Component
public class PlayerFactory {

  @Inject
  private IdFactory<Player> playerIdFactory;

  @Inject
  private EntityFactory entityFactory;

  public Player newPlayer(MochaConnection mochaConnection) {
    return Player.builder()
        .id(playerIdFactory.newId())
        .mochaConnection(mochaConnection)
        .entity(entityFactory.createSlider())
        .build();
  }
}
