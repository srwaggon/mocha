package mocha.net.packet;

public interface PacketHandler<T extends Packet> {

  void handle(T t);
}
