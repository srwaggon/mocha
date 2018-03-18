package mocha.client;

import com.google.common.eventbus.Subscribe;

import mocha.client.event.MochaClientEventBus;
import mocha.game.Game;
import mocha.game.world.Location;
import mocha.game.world.chunk.Chunk;
import mocha.game.world.chunk.ChunkFactory;
import mocha.net.packet.SimplePacketHandler;
import mocha.net.packet.event.ReadPacketEvent;
import mocha.net.packet.world.chunk.ChunkPacket;
import mocha.net.packet.world.entity.EntityPacket;
import mocha.net.packet.world.entity.movement.action.MovePacket;

public class ClientPacketHandler extends SimplePacketHandler {

  private final ChunkFactory chunkFactory;
  private final Game game;
  private MochaClientEventBus eventBus;

  public ClientPacketHandler(ChunkFactory chunkFactory, Game game, MochaClientEventBus eventBus) {
    this.chunkFactory = chunkFactory;
    this.game = game;
    this.eventBus = eventBus;
  }

  @Subscribe
  public void handle(ReadPacketEvent readPacketEvent) {
    eventBus.post(readPacketEvent.getPacket());
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
    eventBus.post(movePacket.getMoveCommand());
  }
}
