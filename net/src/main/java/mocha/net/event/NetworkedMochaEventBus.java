package mocha.net.event;

import com.google.common.collect.Sets;

import java.util.Set;

import mocha.game.event.MochaEventBus;
import mocha.game.world.Location;
import mocha.net.packet.MochaConnection;
import mocha.net.packet.Packet;
import mocha.net.packet.event.ReadPacketEvent;
import mocha.net.packet.event.SendPacketEvent;
import mocha.net.packet.event.SendPacketToAllExceptEvent;
import mocha.net.packet.event.SendPacketToAllNearEvent;
import mocha.net.packet.event.SendPacketToEvent;

public class NetworkedMochaEventBus extends MochaEventBus {

  public void postConnectedEvent(MochaConnection mochaConnection) {
    this.post(new ConnectedEvent(mochaConnection));
  }

  public void postDisconnectedEvent(MochaConnection mochaConnection) {
    this.post(new DisconnectedEvent(mochaConnection));
  }

  public void postSendPacketEvent(Packet packet) {
    this.post(new SendPacketEvent(packet));
  }

  public void postSendPacketToEvent(Packet packet, int playerId) {
    this.post(new SendPacketToEvent(packet, playerId));
  }

  public void postSendPacketToAllExceptEvent(Packet packet, Integer... playerIds) {
    this.post(new SendPacketToAllExceptEvent(packet, Sets.newHashSet(playerIds)));
  }

  public void postSendPacketToAllExceptEvent(Packet packet, Set<Integer> playerIds) {
    this.post(new SendPacketToAllExceptEvent(packet, playerIds));
  }

  public void postSendPacketToAllNearEvent(Packet packet, Location location, int distance) {
    this.post(new SendPacketToAllNearEvent(packet, location, distance));
  }

  public void postReadPacketEvent(int senderId, Packet packet) {
    this.post(new ReadPacketEvent(senderId, packet));
  }

}
