package mocha.server;

import com.google.common.eventbus.EventBus;

import org.springframework.stereotype.Component;

import javax.inject.Inject;

import mocha.game.Game;
import mocha.net.MochaConnection;

@Component
public class ServerPacketHandlerFactory {

  @Inject
  private EventBus eventBus;

  @Inject
  private Game game;

  public ServerPacketHandler newServerPacketHandler(MochaConnection mochaConnection) {
    ServerPacketHandler serverPacketHandler = new ServerPacketHandler(mochaConnection, game);
    eventBus.register(serverPacketHandler);
    return serverPacketHandler;
  }
}
