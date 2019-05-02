package mocha.server;

import com.google.common.collect.Queues;
import com.google.common.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

import mocha.game.GameLogic;
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

  private int playerId;
  private MochaConnection mochaConnection;
  private EventBus packetEventBus;

  private ConcurrentLinkedQueue<Packet> packets = Queues.newConcurrentLinkedQueue();

  private List<PacketHandler> packetHandlers = new ArrayList<>();

  ServerPacketResolver(
      MochaConnection mochaConnection,
      ServerEventBus serverEventBus,
      int playerId,
      ChunkService chunkService,
      EntitiesInChunkService entitiesInChunkService,
      GameLogic gameLogic,
      EntityService entityService,
      PlayerService playerService
  ) {
    this.mochaConnection = mochaConnection;
    this.playerId = playerId;
    packetEventBus = new EventBus();

    packetHandlers.add(new RequestEntitiesByPlayerIdPacketHandler(mochaConnection, entityService, playerService));
    packetHandlers.add(new RequestChunkByIdPacketHandler(mochaConnection, chunkService));
    packetHandlers.add(new RequestChunkByLocationPacketHandler(mochaConnection, chunkService));
    packetHandlers.add(new RequestEntityByIdPacketHandler(mochaConnection, entityService));
    packetHandlers.add(new RequestEntitiesInChunkPacketHandler(mochaConnection, chunkService, entitiesInChunkService));
    packetHandlers.add(new MovePacketHandler(playerId, serverEventBus, playerService));
    packetHandlers.add(new PickUpItemPacketHandler(entityService, serverEventBus));
    packetHandlers.add(new ItemPrototypeUpdatePacketHandler(gameLogic));
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
        packetEventBus.post(packets.poll());
        nap();
      }
    }
  }

  public void resolve(int senderId, Packet packet) {
    if (senderId == playerId) {
      packets.offer(packet);
    }
  }

}
