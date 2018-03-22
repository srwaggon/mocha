package mocha.server;

import com.google.common.collect.Queues;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

import java.util.concurrent.ConcurrentLinkedQueue;

import mocha.game.Game;
import mocha.game.world.Location;
import mocha.game.world.chunk.RequestChunkPacket;
import mocha.game.world.entity.RequestEntitiesInChunkPacket;
import mocha.game.world.entity.event.EntityAddedEvent;
import mocha.game.world.entity.event.EntityRemovedEvent;
import mocha.game.world.entity.movement.MovePacket;
import mocha.game.world.entity.movement.Movement;
import mocha.game.world.entity.movement.command.EntityMoveCommand;
import mocha.game.world.entity.movement.event.EntityMovementEvent;
import mocha.net.event.NetworkedMochaEventBus;
import mocha.net.packet.MochaConnection;
import mocha.net.packet.Packet;
import mocha.net.packet.SimplePacketHandler;
import mocha.net.packet.event.ReadPacketEvent;
import mocha.shared.task.SleepyRunnable;

public class ServerPacketHandler extends SimplePacketHandler implements SleepyRunnable {
  private final int playerId;
  private NetworkedMochaEventBus eventBus;
  private MochaConnection mochaConnection;
  private Game game;
  private EventBus packetEventBus;

  private ConcurrentLinkedQueue<Packet> packets = Queues.newConcurrentLinkedQueue();

  ServerPacketHandler(MochaConnection mochaConnection, Game game, NetworkedMochaEventBus eventBus, int playerId) {
    this.mochaConnection = mochaConnection;
    this.game = game;
    this.eventBus = eventBus;
    this.playerId = playerId;
    packetEventBus = new EventBus();
    packetEventBus.register(this);
  }

  public void remove() {
    eventBus.unregister(this);
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

  @Subscribe
  public void handle(ReadPacketEvent readPacketEvent) {
    if (readPacketEvent.getSenderId() == playerId) {
      packets.offer(readPacketEvent.getPacket());
    }
  }

  @Subscribe
  public void handle(EntityAddedEvent entityAddedEvent) {
    mochaConnection.sendEntityUpdate(entityAddedEvent.getEntity());
  }

  @Subscribe
  public void handle(EntityMovementEvent entityMovementEvent) {
    Movement movement = entityMovementEvent.getMovement();
    EntityMoveCommand entityMove = EntityMoveCommand.builder()
        .entityId(movement.getEntity().getId())
        .location(movement.getLocation())
        .direction(movement.getDirection())
        .xOffset(movement.getXOffset())
        .yOffset(movement.getYOffset())
        .build();
    mochaConnection.sendMoveCommand(entityMove);
  }

  @Subscribe
  public void handle(EntityRemovedEvent entityRemovedEvent) {
    mochaConnection.sendEntityRemoved(entityRemovedEvent.getEntity());
  }

  @Subscribe
  @Override
  public void handle(RequestChunkPacket requestChunkPacket) {
    Location location = requestChunkPacket.getLocation();
    game.getWorld().getChunkAt(location)
        .ifPresent(chunk -> mochaConnection.sendChunkUpdate(location, chunk));
  }

  @Subscribe
  @Override
  public void handle(RequestEntitiesInChunkPacket requestEntitiesInChunkPacket) {
    Location location = requestEntitiesInChunkPacket.getLocation();
    game.getWorld().getChunkAt(location).ifPresent(chunk ->
        chunk.getEntities().forEach(mochaConnection::sendEntityUpdate));
  }

  @Subscribe
  @Override
  public void handle(MovePacket movePacket) {
    game.getPlayerRegistry().get(playerId).ifPresent(player -> {
      EntityMoveCommand moveCommand = movePacket.getMoveCommand();
      if (player.getEntity().getId() == moveCommand.getEntityId()) {
        eventBus.post(moveCommand);
      }
    });
  }
}
