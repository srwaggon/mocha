package mocha.client;

import com.google.common.eventbus.Subscribe;

import mocha.game.Game;
import mocha.game.world.Location;
import mocha.game.world.chunk.Chunk;
import mocha.game.world.chunk.ChunkFactory;
import mocha.net.SimplePacketHandler;
import mocha.net.packet.world.chunk.ChunkPacket;
import mocha.net.packet.world.entity.EntityPacket;

public class ClientPacketHandler extends SimplePacketHandler {

  private final ChunkFactory chunkFactory;
  private final Game game;

  ClientPacketHandler(ChunkFactory chunkFactory, Game game) {
    this.chunkFactory = chunkFactory;
    this.game = game;
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
    game.add(entityPacket.getEntity());
  }
}
