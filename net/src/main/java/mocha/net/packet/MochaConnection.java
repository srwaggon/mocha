package mocha.net.packet;

import java.net.Socket;

import mocha.game.world.Location;
import mocha.game.world.chunk.Chunk;
import mocha.game.world.entity.Entity;
import mocha.game.world.entity.movement.command.EntityMoveCommand;

public class MochaConnection extends PacketConnection {

  private PacketFactory packetFactory;

  public MochaConnection(Socket socket, PacketFactory packetFactory) {
    super(socket);
    this.packetFactory = packetFactory;
  }

  public void sendChunkUpdate(Location location, Chunk chunk) {
    sendPacket(packetFactory.newChunkPacket(location, chunk));
  }

  public void sendEntityUpdate(Entity entity) {
    sendPacket(packetFactory.entityPacket(entity));
  }

  public void sendMoveCommand(EntityMoveCommand entityMoveCommand) {
    sendPacket(packetFactory.movePacket(entityMoveCommand));
  }

}
