package mocha.client;

import com.google.common.collect.Queues;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentLinkedQueue;

import javax.inject.Inject;

import mocha.client.event.ClientEventBus;
import mocha.game.Game;
import mocha.game.world.Location;
import mocha.game.world.chunk.Chunk;
import mocha.game.world.chunk.ChunkFactory;
import mocha.game.world.chunk.ChunkPacket;
import mocha.game.world.entity.EntityPacket;
import mocha.game.world.entity.EntityRemovedPacket;
import mocha.game.world.entity.movement.MovePacket;
import mocha.game.world.entity.movement.command.EntityMoveCommand;
import mocha.net.packet.Packet;
import mocha.net.packet.SimplePacketHandler;
import mocha.net.packet.event.ReadPacketEvent;
import mocha.shared.task.SleepyRunnable;

@Component
public class ClientPacketHandler extends SimplePacketHandler implements SleepyRunnable {

  private static final Logger log = LoggerFactory.getLogger(ClientPacketHandler.class);

  @Inject
  private ChunkFactory chunkFactory;
  @Inject
  private Game game;
  @Inject
  private ClientEventBus eventBus;

  private EventBus packetEventBus = new EventBus();

  private ConcurrentLinkedQueue<Packet> packets = Queues.newConcurrentLinkedQueue();

  @Override
  public void run() {
    packetEventBus.register(this);
    //noinspection InfiniteLoopStatement
    while (true) {
      if (!packets.isEmpty()) {
        packetEventBus.post(packets.poll());
        nap();
      }
    }
  }

  @Subscribe
  public void handle(ReadPacketEvent readPacketEvent) {
    packets.offer(readPacketEvent.getPacket());
  }

  @Subscribe
  @Override
  public void handle(ChunkPacket chunkPacket) {
    Location location = chunkPacket.getLocation();
    Chunk chunk = chunkFactory.read(chunkPacket.getChunkDescription());
    game.getWorld().put(location, chunk);
  }

  @Subscribe
  @Override
  public void handle(EntityPacket entityPacket) {
    eventBus.postEntityUpdatedEvent(entityPacket.getEntity());
  }

  @Subscribe
  @Override
  public void handle(MovePacket movePacket) {
    EntityMoveCommand moveCommand = movePacket.getMoveCommand();
    game.getEntityRegistry().get(moveCommand.getEntityId())
        .ifPresent(entity -> entity.getLocation().set(moveCommand.getLocation()));
    eventBus.post(moveCommand);
  }

  @Subscribe
  public void handle(EntityRemovedPacket entityRemovedPacket) {
    game.removeEntity(entityRemovedPacket.getId());
  }
}
