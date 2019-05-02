package mocha.game.world.chunk;

import com.google.common.eventbus.Subscribe;

import org.springframework.stereotype.Component;

import javax.inject.Inject;

import mocha.game.world.Location;
import mocha.game.world.chunk.tile.TileSetFactory;
import mocha.game.world.chunk.tile.TileType;
import mocha.net.packet.PacketHandler;

@Component
public class ChunkUpdatePacketHandler implements PacketHandler {

  private TileSetFactory tileSetFactory;
  private ChunkService chunkService;

  @Inject
  public ChunkUpdatePacketHandler(TileSetFactory tileSetFactory, ChunkService chunkService) {
    this.tileSetFactory = tileSetFactory;
    this.chunkService = chunkService;
  }

  @Subscribe
  public void handle(ChunkUpdatePacket chunkUpdatePacket) {
    int chunkId = chunkUpdatePacket.getChunkId();
    Location location = new Location(chunkUpdatePacket.getX(), chunkUpdatePacket.getY());
    TileType[] tiles = tileSetFactory.newTilesFromString(chunkUpdatePacket.getTilesString());
    Chunk chunk = new Chunk(chunkId, location, tiles);
    chunkService.save(chunk);
  }
}