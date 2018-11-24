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
import mocha.game.LocalPlayer;
import mocha.game.LoginSuccessPacket;
import mocha.game.Player;
import mocha.game.PlayerIdentityPacket;
import mocha.game.world.chunk.Chunk;
import mocha.game.world.chunk.ChunkPacket;
import mocha.game.world.entity.Entity;
import mocha.game.world.entity.EntityPacket;
import mocha.game.world.entity.EntityRemovedPacket;
import mocha.game.world.entity.RequestEntitiesByPlayerIdPacket;
import mocha.game.world.entity.movement.MovePacket;
import mocha.game.world.tile.TileSetFactory;
import mocha.game.world.tile.TileType;
import mocha.net.packet.Packet;
import mocha.net.packet.SimplePacketHandler;
import mocha.shared.Repository;
import mocha.shared.task.SleepyRunnable;

@Component
public class ClientPacketHandler extends SimplePacketHandler implements SleepyRunnable {

  private static final Logger log = LoggerFactory.getLogger(ClientPacketHandler.class);

  @Inject
  private Game game;
  @Inject
  private ClientEventBus clientEventBus;
  @Inject
  private Repository<Player, Integer> playerRepository;
  @Inject
  private Repository<Entity, Integer> entityRepository;
  @Inject
  private Repository<Chunk, Integer> chunkRepository;
  @Inject
  private TileSetFactory tileSetFactory;


  private EventBus packetEventBus = new EventBus();

  private ConcurrentLinkedQueue<Packet> packets = Queues.newConcurrentLinkedQueue();

  @Override
  public void run() {
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
  public void handle(LoginSuccessPacket loginSuccessPacket) {
    game.addPlayer(new LocalPlayer(loginSuccessPacket.getPlayerId()));
  }

  @Subscribe
  public void handle(PlayerIdentityPacket playerIdentityPacket) {

  }

  @Subscribe
  public void handle(RequestEntitiesByPlayerIdPacket requestEntitiesByPlayerIdPacket) {
    playerRepository.findById(requestEntitiesByPlayerIdPacket.getPlayerId())
        .ifPresent(player -> requestEntitiesByPlayerIdPacket.getEntityIds()
            .forEach(entityId -> entityRepository.findById(entityId)
                .ifPresent(player::setEntity)));
  }

  @Subscribe
  @Override
  public void handle(ChunkPacket chunkPacket) {
    int chunkId = chunkPacket.getChunkId();
    TileType[] tiles = tileSetFactory.newTilesFromString(chunkPacket.getTilesString());
    Chunk chunk = new Chunk(chunkId, tiles);

    chunkRepository.save(chunk);
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
