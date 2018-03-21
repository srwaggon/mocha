package mocha.server;

import com.google.common.collect.Queues;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

import java.util.concurrent.ConcurrentLinkedQueue;

import mocha.game.Game;
import mocha.game.world.Location;
import mocha.game.world.entity.event.AddEntityEvent;
import mocha.game.world.entity.movement.Movement;
import mocha.game.world.entity.movement.command.EntityMoveCommand;
import mocha.game.world.entity.movement.event.EntityMovementEvent;
import mocha.net.event.NetworkedMochaEventBus;
import mocha.net.packet.MochaConnection;
import mocha.net.packet.Packet;
import mocha.net.packet.SimplePacketHandler;
import mocha.net.packet.event.ReadPacketEvent;
import mocha.net.packet.world.chunk.RequestChunkPacket;
import mocha.net.packet.world.entity.RequestEntitiesInChunkPacket;
import mocha.net.packet.world.entity.movement.action.MovePacket;
import mocha.shared.task.SleepyRunnable;

public class ServerPacketHandler extends SimplePacketHandler implements SleepyRunnable {
  private NetworkedMochaEventBus eventBus;
  private final int clientId;
  private MochaConnection mochaConnection;
  private Game game;
  private EventBus packetEventBus;

  private ConcurrentLinkedQueue<Packet> packets = Queues.newConcurrentLinkedQueue();

  ServerPacketHandler(MochaConnection mochaConnection, Game game, NetworkedMochaEventBus eventBus, int clientId) {
    this.mochaConnection = mochaConnection;
    this.game = game;
    this.eventBus = eventBus;
    this.clientId = clientId;
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
    if (readPacketEvent.getSenderId() == this.clientId) {
      packets.offer(readPacketEvent.getPacket());
    }
  }

  @Subscribe
  public void handle(AddEntityEvent addEntityEvent) {
    mochaConnection.sendEntityUpdate(addEntityEvent.getEntity());
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
    eventBus.post(movePacket.getMoveCommand());
  }
}
