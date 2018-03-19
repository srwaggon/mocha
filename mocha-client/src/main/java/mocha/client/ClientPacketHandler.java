package mocha.client;

import com.google.common.collect.Queues;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

import java.util.concurrent.ConcurrentLinkedQueue;

import mocha.client.event.ClientEventBus;
import mocha.game.Game;
import mocha.game.world.Location;
import mocha.game.world.chunk.Chunk;
import mocha.game.world.chunk.ChunkFactory;
import mocha.game.world.entity.movement.command.EntityMoveCommand;
import mocha.net.packet.Packet;
import mocha.net.packet.SimplePacketHandler;
import mocha.net.packet.event.ReadPacketEvent;
import mocha.net.packet.world.chunk.ChunkPacket;
import mocha.net.packet.world.entity.EntityPacket;
import mocha.net.packet.world.entity.movement.action.MovePacket;
import mocha.shared.task.SleepyRunnable;

public class ClientPacketHandler extends SimplePacketHandler implements SleepyRunnable {

  private final ChunkFactory chunkFactory;
  private final Game game;
  private ClientEventBus eventBus;

  private EventBus packetEventBus;

  private ConcurrentLinkedQueue<Packet> packets = Queues.newConcurrentLinkedQueue();

  public ClientPacketHandler(ChunkFactory chunkFactory, Game game, ClientEventBus eventBus) {
    this.chunkFactory = chunkFactory;
    this.game = game;
    this.eventBus = eventBus;
    packetEventBus = new EventBus();
    packetEventBus.register(this);
  }

  @Override
  public void run() {
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
    eventBus.postEntityUpdateEvent(entityPacket.getEntity());
  }

  @Subscribe
  @Override
  public void handle(MovePacket movePacket) {
    EntityMoveCommand moveCommand = movePacket.getMoveCommand();
    game.getEntityRegistry().get(moveCommand.getEntityId())
        .ifPresent(entity -> entity.getLocation().set(moveCommand.getLocation()));
    eventBus.post(moveCommand);
  }
}
