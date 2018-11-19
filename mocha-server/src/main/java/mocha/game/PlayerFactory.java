package mocha.game;

import org.springframework.stereotype.Component;

import javax.inject.Inject;

import mocha.game.world.World;
import mocha.game.world.entity.Entity;
import mocha.game.world.entity.EntityFactory;
import mocha.net.packet.MochaConnection;
import mocha.net.packet.PacketListener;
import mocha.server.ServerPacketHandler;
import mocha.server.event.ServerEventBus;
import mocha.shared.IdFactory;
import mocha.shared.Repository;

@Component
public class PlayerFactory {

  @Inject
  private ServerEventBus serverEventBus;

  @Inject
  private IdFactory<Player> playerIdFactory;

  @Inject
  private EntityFactory entityFactory;

  @Inject
  private Game game;

  @Inject
  private World world;

  @Inject
  private Repository<Player, Integer> playerRepository;

  @Inject
  private Repository<Entity, Integer> entityRepository;

  NetworkPlayer newNetworkPlayer(MochaConnection mochaConnection) {
    int playerId = playerIdFactory.newId();

    ServerPacketHandler serverPacketHandler = newServerPacketHandler(mochaConnection, playerId);
    PacketListener packetListener = newPacketListener(mochaConnection, playerId, serverPacketHandler);

    serverEventBus.postTaskEvent(packetListener);
    serverEventBus.postTaskEvent(serverPacketHandler);

    return NetworkPlayer.builder()
        .id(playerId)
        .entity(entityFactory.newSlider())
        .packetListener(packetListener)
        .serverPacketHandler(serverPacketHandler)
        .build();
  }

  private ServerPacketHandler newServerPacketHandler(
      MochaConnection mochaConnection,
      int playerId
  ) {
    return new ServerPacketHandler(
        mochaConnection,
        serverEventBus,
        playerId,
        world,
        entityRepository,
        playerRepository
    );
  }

  private PacketListener newPacketListener(
      MochaConnection mochaConnection,
      int playerId,
      ServerPacketHandler serverPacketHandler
  ) {
    return new PacketListener(
        serverEventBus,
        mochaConnection,
        playerId,
        serverPacketHandler
    );
  }
}
