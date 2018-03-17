package mocha.net.packet;

import mocha.game.world.Location;
import mocha.game.world.chunk.Chunk;
import mocha.game.world.entity.Entity;

public class MochaConnection {

  private PacketConnection packetConnection;
  private PacketFactory packetFactory;

  public MochaConnection(PacketConnection packetConnection, PacketFactory packetFactory) {
    this.packetConnection = packetConnection;
    this.packetFactory = packetFactory;
  }

  public void sendChunkUpdate(Location location, Chunk chunk) {
    packetConnection.sendPacket(packetFactory.newChunkPacket(location, chunk));
  }

  public void sendEntityUpdate(Entity entity) {
    packetConnection.sendPacket(packetFactory.entityPacket(entity));
  }

}
