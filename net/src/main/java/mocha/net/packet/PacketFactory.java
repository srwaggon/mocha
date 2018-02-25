package mocha.net.packet;

import mocha.game.world.Location;
import mocha.game.world.World;
import mocha.game.world.chunk.Chunk;
import mocha.net.packet.world.chunk.ChunkPacket;
import mocha.net.packet.world.chunk.ChunkRequestPacket;

public class PacketFactory {

  private final World world;

  public PacketFactory(World world) {
    this.world = world;
  }

  public ChunkPacket newChunkPacket() {
    // TODO: take chunk
    Location location = new Location(0, 0);
    Chunk chunk = world.getChunkAt(location).get();
    String[] data = new String[2];
    data[1] = chunk.buildTileData();

    ChunkPacket chunkPacket = new ChunkPacket();
    chunkPacket.build(data);
    return chunkPacket;
  }

  public ChunkRequestPacket newChunkRequestPacket() {
    // TODO: Take location
    Location location = new Location(0, 0);
    String[] data = new String[3];
    data[1] = "" + location.getXAsInt();
    data[2] = "" + location.getYAsInt();

    ChunkRequestPacket chunkRequestPacket = new ChunkRequestPacket();
    chunkRequestPacket.build(data);
    return chunkRequestPacket;
  }
}
