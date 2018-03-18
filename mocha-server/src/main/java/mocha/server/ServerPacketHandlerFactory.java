package mocha.server;

import com.google.common.eventbus.EventBus;

import org.springframework.stereotype.Component;

import javax.inject.Inject;

import mocha.game.Game;
import mocha.net.event.NetworkedMochaEventBus;
import mocha.net.packet.MochaConnection;
import mocha.net.packet.PacketFactory;

@Component
public class ServerPacketHandlerFactory {

  @Inject
  private EventBus eventBus;

  @Inject
  private Game game;

  @Inject
  private PacketFactory packetFactory;

  @Inject
  private NetworkedMochaEventBus networkedMochaEventBus;

  public ServerPacketHandler newServerPacketHandler(MochaConnection mochaConnection, int clientId) {
    return new ServerPacketHandler(mochaConnection, game, networkedMochaEventBus, clientId);
  }
}
