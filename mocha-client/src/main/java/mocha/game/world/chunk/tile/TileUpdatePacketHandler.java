package mocha.game.world.chunk.tile;

import com.google.common.eventbus.Subscribe;

import org.springframework.stereotype.Component;

import javax.inject.Inject;

import mocha.game.world.chunk.ChunkService;
import mocha.net.packet.PacketHandler;

@Component
public class TileUpdatePacketHandler implements PacketHandler<TileUpdatePacket> {

  private ChunkService chunkService;

  @Inject
  public TileUpdatePacketHandler(ChunkService chunkService) {
    this.chunkService = chunkService;
  }

  @Subscribe
  public void handle(TileUpdatePacket tileUpdatePacket) {
    chunkService.updateTileTypeAt(tileUpdatePacket.getLocation(), tileUpdatePacket.getTileType());
  }
}