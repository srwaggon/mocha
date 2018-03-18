package mocha.net.event;

import mocha.game.event.MochaEventBus;
import mocha.game.world.entity.movement.command.EntityMoveCommand;
import mocha.net.packet.MochaConnection;
import mocha.net.packet.Packet;
import mocha.net.packet.PacketFactory;
import mocha.net.packet.SendPacketEvent;

public class NetworkedMochaEventBus extends MochaEventBus {

  private PacketFactory packetFactory;

  public NetworkedMochaEventBus(PacketFactory packetFactory) {
    this.packetFactory = packetFactory;
  }

  public void connected(MochaConnection mochaConnection) {
    this.post(new ConnectedEvent(mochaConnection));
  }

  public void disconnected(MochaConnection mochaConnection) {
    this.post(new DisconnectedEvent(mochaConnection));
  }

  public void sendPacket(Packet packet) {
    this.post(new SendPacketEvent(packet));
  }

  public void sendMovePacket(EntityMoveCommand entityMove) {
    sendPacket(packetFactory.movePacket(entityMove));
  }
}
