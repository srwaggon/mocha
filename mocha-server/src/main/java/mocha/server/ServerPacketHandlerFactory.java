package mocha.server;

import com.google.common.eventbus.EventBus;

import org.springframework.stereotype.Component;

import javax.inject.Inject;

import mocha.game.Game;
import mocha.net.packet.MochaConnection;
import mocha.net.packet.PacketFactory;
import mocha.server.event.ServerEventBus;

@Component
public class ServerPacketHandlerFactory {

  @Inject
  private EventBus eventBus;

  @Inject
  private Game game;

  @Inject
  private PacketFactory packetFactory;

  @Inject
  private ServerEventBus serverEventBus;

  public ServerPacketHandler newServerPacketHandler(MochaConnection mochaConnection, int clientId) {
    return new ServerPacketHandler(mochaConnection, game, serverEventBus, clientId);
  }
}
