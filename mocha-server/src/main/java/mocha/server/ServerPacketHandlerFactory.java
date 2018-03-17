package mocha.server;

import com.google.common.eventbus.EventBus;

import org.springframework.stereotype.Component;

import javax.inject.Inject;

import mocha.game.Game;
import mocha.net.packet.PacketConnection;
import mocha.net.event.NetworkedMochaEventBus;
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

  public void newServerPacketHandler(PacketConnection packetConnection) {
    ServerPacketHandler serverPacketHandler = new ServerPacketHandler(packetConnection, packetFactory, game, networkedMochaEventBus);
    eventBus.register(serverPacketHandler);
  }
}
