package mocha.server;

import com.google.common.collect.Queues;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

import java.util.Optional;
import java.util.concurrent.ConcurrentLinkedQueue;

import mocha.game.Player;
import mocha.game.world.Location;
import mocha.game.world.World;
import mocha.game.world.chunk.RequestChunkPacket;
import mocha.game.world.entity.Entity;
import mocha.game.world.entity.RequestEntitiesInChunkPacket;
import mocha.game.world.entity.RequestEntityByIdPacket;
import mocha.game.world.entity.movement.MovePacket;
import mocha.game.world.entity.movement.command.EntityMoveCommand;
import mocha.net.packet.MochaConnection;
import mocha.net.packet.Packet;
import mocha.net.packet.SimplePacketHandler;
import mocha.server.event.ServerEventBus;
import mocha.shared.Repository;
import mocha.shared.task.SleepyRunnable;

public class ServerPacketHandler extends SimplePacketHandler implements SleepyRunnable {
  private final int playerId;
  private ServerEventBus serverEventBus;
  private MochaConnection mochaConnection;
  private EventBus packetEventBus;
  private World world;
  private Repository<Entity, Integer> entityRepository;
  private Repository<Player, Integer> playerRepository;

  private ConcurrentLinkedQueue<Packet> packets = Queues.newConcurrentLinkedQueue();

  public ServerPacketHandler(
      MochaConnection mochaConnection,
      ServerEventBus serverEventBus,
      int playerId,
      World world,
      Repository<Entity, Integer> entityRepository,
      Repository<Player, Integer> playerRepository
  ) {
    this.mochaConnection = mochaConnection;
    this.serverEventBus = serverEventBus;
    this.playerId = playerId;
    this.world = world;
    this.entityRepository = entityRepository;
    this.playerRepository = playerRepository;
    packetEventBus = new EventBus();
    packetEventBus.register(this);
  }

  public void remove() {
    packetEventBus.unregister(this);
  }

  @Override
  public void run() {
    while (mochaConnection.isConnected()) {
      if (!packets.isEmpty()) {
        packetEventBus.post(packets.poll());
        nap();
      }
    }
  }

  @Override
  public void handle(int senderId, Packet packet) {
    if (senderId == playerId) {
      packets.offer(packet);
    }
  }

  @Subscribe
  @Override
  public void handle(RequestChunkPacket requestChunkPacket) {
    Location location = requestChunkPacket.getLocation();
    world.getChunkAt(location)
        .ifPresent(chunk -> mochaConnection.sendChunkUpdate(location, chunk));
  }

  @Override
  @Subscribe
  public void handle(RequestEntityByIdPacket requestEntityByIdPacket) {
    int entityId = requestEntityByIdPacket.getId();
    Optional<Entity> optionalEntity = entityRepository.findById(entityId);
    if (optionalEntity.isPresent()) {
      mochaConnection.sendEntityUpdate(optionalEntity.get());
    } else {
      mochaConnection.sendEntityRemoved(new Entity(entityId, new Location()));
    }
  }

  @Subscribe
  @Override
  public void handle(RequestEntitiesInChunkPacket requestEntitiesInChunkPacket) {
    Location location = requestEntitiesInChunkPacket.getLocation();
    world.getChunkAt(location)
        .ifPresent(chunk ->
            chunk.getEntities().forEach(mochaConnection::sendEntityUpdate));
  }

  @Subscribe
  @Override
  public void handle(MovePacket movePacket) {
    playerRepository.findById(playerId)
        .ifPresent(player -> {
          EntityMoveCommand moveCommand = movePacket.getMoveCommand();
          moveCommand.setEntityId(player.getEntity().getId());
          serverEventBus.post(moveCommand);
        });
  }
}
