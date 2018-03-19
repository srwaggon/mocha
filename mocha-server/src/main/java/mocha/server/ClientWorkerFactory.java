package mocha.server;

import org.springframework.stereotype.Component;

import javax.inject.Inject;

import mocha.game.IdFactory;
import mocha.game.world.entity.EntityFactory;
import mocha.net.event.NetworkedMochaEventBus;
import mocha.net.packet.MochaConnection;
import mocha.net.packet.PacketListener;
import mocha.net.packet.PacketListenerFactory;

@Component
public class ClientWorkerFactory {

  @Inject
  private IdFactory<ClientWorker> clientWorkerIdFactory;

  @Inject
  private NetworkedMochaEventBus eventBus;

  @Inject
  private EntityFactory entityFactory;

  @Inject
  private ServerPacketHandlerFactory serverPacketHandlerFactory;

  @Inject
  private PacketListenerFactory packetListenerFactory;

  public ClientWorker newClientWorker(MochaConnection mochaConnection) {

    int clientId = clientWorkerIdFactory.newId();

    PacketListener packetListener = packetListenerFactory.newPacketListener(mochaConnection, clientId);
    eventBus.postTaskEvent(packetListener);

    ServerPacketHandler serverPacketHandler = serverPacketHandlerFactory.newServerPacketHandler(mochaConnection, clientId);
    eventBus.postTaskEvent(serverPacketHandler);
    eventBus.register(serverPacketHandler);

    return ClientWorker.builder()
        .id(clientId)
        .mochaConnection(mochaConnection)
        .entity(entityFactory.createSlider())
        .packetListener(packetListener)
        .serverPacketHandler(serverPacketHandler)
        .build();
  }
}
