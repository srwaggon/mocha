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
import mocha.net.packet.Packet;
import mocha.net.packet.SimplePacketHandler;
import mocha.shared.task.SleepyRunnable;

@Component
public class ClientPacketHandler extends SimplePacketHandler implements SleepyRunnable {

  private static final Logger log = LoggerFactory.getLogger(ClientPacketHandler.class);

  @Inject
  private ChunkFactory chunkFactory;
  @Inject
  private Game game;
  @Inject
  private ClientEventBus clientEventBus;

  private EventBus packetEventBus = new EventBus();

  private ConcurrentLinkedQueue<Packet> packets = Queues.newConcurrentLinkedQueue();

  @Override
  public void run() {
    clientEventBus.register(this);
    packetEventBus.register(this);
    //noinspection InfiniteLoopStatement
    while (true) {
      if (!packets.isEmpty()) {
        Packet packet = packets.poll();
        System.out.println(packet);
        packetEventBus.post(packet);
        nap();
      }
    }
  }

  @Override
  public void handle(int senderId, Packet packet) {
    packets.offer(packet);
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
    clientEventBus.postEntityUpdatedEvent(entityPacket.getEntity());
  }

  @Subscribe
  @Override
  public void handle(MovePacket movePacket) {
    clientEventBus.post(movePacket.getMoveCommand());
  }

  @Subscribe
  public void handle(EntityRemovedPacket entityRemovedPacket) {
    game.removeEntity(entityRemovedPacket.getId());
  }
}
