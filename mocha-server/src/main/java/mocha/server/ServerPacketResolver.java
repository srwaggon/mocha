package mocha.server;

import com.google.common.collect.Queues;
import com.google.common.eventbus.EventBus;

import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

import mocha.game.player.PlayerService;
import mocha.game.world.chunk.ChunkService;
import mocha.game.world.chunk.RequestChunkByIdPacketHandler;
import mocha.game.world.chunk.RequestChunkByLocationPacketHandler;
import mocha.game.world.entity.EntitiesInChunkService;
import mocha.game.world.entity.EntityService;
import mocha.game.world.entity.RequestEntitiesByPlayerIdPacketHandler;
import mocha.game.world.entity.RequestEntitiesInChunkPacketHandler;
import mocha.game.world.entity.RequestEntityByIdPacketHandler;
import mocha.game.world.entity.movement.MovePacketHandler;
import mocha.game.world.item.ItemPrototypeUpdatePacketHandler;
import mocha.game.world.item.PickUpItemPacketHandler;
import mocha.net.packet.MochaConnection;
import mocha.net.packet.Packet;
import mocha.net.packet.PacketHandler;
import mocha.net.packet.PacketResolver;
import mocha.server.event.ServerEventBus;
import mocha.shared.task.SleepyRunnable;

public class ServerPacketResolver implements PacketResolver, SleepyRunnable {

  private MochaConnection mochaConnection;
  private EventBus packetEventBus = new EventBus();

  private ConcurrentLinkedQueue<Packet> packets = Queues.newConcurrentLinkedQueue();

  private List<PacketHandler> packetHandlers;

  ServerPacketResolver(
      MochaConnection mochaConnection,
      ServerEventBus serverEventBus,
      ChunkService chunkService,
      EntitiesInChunkService entitiesInChunkService,
      EntityService entityService,
      PlayerService playerService,
      int playerId,
      List<PacketHandler> packetHandlers
  ) {
    this.mochaConnection = mochaConnection;
    this.packetHandlers = packetHandlers;
    this.packetHandlers.add(new RequestEntitiesByPlayerIdPacketHandler(mochaConnection, entityService, playerService));
    this.packetHandlers.add(new RequestChunkByIdPacketHandler(mochaConnection, chunkService));
    this.packetHandlers.add(new RequestChunkByLocationPacketHandler(mochaConnection, chunkService));
    this.packetHandlers.add(new RequestEntityByIdPacketHandler(mochaConnection, entityService));
    this.packetHandlers.add(new RequestEntitiesInChunkPacketHandler(mochaConnection, chunkService, entitiesInChunkService));
    this.packetHandlers.add(new MovePacketHandler(serverEventBus, playerId, playerService));
    this.packetHandlers.add(new PickUpItemPacketHandler(entityService, serverEventBus));
    this.packetHandlers.add(new ItemPrototypeUpdatePacketHandler(serverEventBus));
  }

  private void setup() {
    packetHandlers.forEach(packetEventBus::register);
  }

  public void remove() {
    packetHandlers.forEach(packetEventBus::unregister);
  }

  @Override
  public void run() {
    setup();
    while (mochaConnection.isConnected()) {
      if (!packets.isEmpty()) {
        Packet packet = packets.poll();
        packetEventBus.post(packet);
        nap();
      }
    }
  }

  public void resolve(Packet packet) {
    packets.offer(packet);
  }

}
