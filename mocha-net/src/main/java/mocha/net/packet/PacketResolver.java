package mocha.net.packet;

public interface PacketResolver {

  void resolve(int senderId, Packet packet);

}
