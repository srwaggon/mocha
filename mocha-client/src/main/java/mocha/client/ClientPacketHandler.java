package mocha.client;

import com.google.common.eventbus.Subscribe;

import mocha.game.world.Location;
import mocha.game.world.World;
import mocha.game.world.chunk.Chunk;
import mocha.game.world.chunk.ChunkDescription;
import mocha.game.world.chunk.ChunkFactory;
import mocha.net.PacketHandler;
import mocha.net.packet.Packet;
import mocha.net.packet.world.chunk.ChunkPacket;

public class ClientPacketHandler implements PacketHandler {

  private final ChunkFactory chunkFactory;
  private final World world;

  public ClientPacketHandler(ChunkFactory chunkFactory, World world) {
    this.chunkFactory = chunkFactory;
    this.world = world;
  }

  @Override
  @Subscribe
  public void handlePacket(Packet packet) {
    System.out.println("handling packet " + packet.construct());
  }

  @Subscribe
  public void handle(ChunkPacket chunkPacket) {
    System.out.println("Handling chunk packet " + chunkPacket.construct());
    String tiles = chunkPacket.getData()[1];
    Chunk chunk = chunkFactory.read(new ChunkDescription(tiles));
    world.getChunks().put(new Location(0,0), chunk);
  }
}
