package mocha.game;

import mocha.game.world.entity.Entity;
import mocha.net.packet.PacketListener;
import mocha.server.ServerPacketHandler;

public class NetworkPlayer implements Player {

  private int id;
  private Entity entity;
  private PacketListener packetListener;
  private ServerPacketHandler serverPacketHandler;

  private NetworkPlayer(int id, Entity entity, PacketListener packetListener, ServerPacketHandler serverPacketHandler) {
    this.id = id;
    this.entity = entity;
    this.packetListener = packetListener;
    this.serverPacketHandler = serverPacketHandler;
  }

  public void remove() {
    packetListener.remove();
    serverPacketHandler.remove();
  }

  @Override
  public int getId() {
    return id;
  }

  public Entity getEntity() {
    return entity;
  }

  public static NetworkPlayerBuilder builder() {
    return new NetworkPlayerBuilder();
  }

  public static class NetworkPlayerBuilder {
    private int id;
    private Entity entity;
    private PacketListener packetListener;
    private ServerPacketHandler serverPacketHandler;

    public NetworkPlayerBuilder id(int id) {
      this.id = id;
      return this;
    }

    public NetworkPlayerBuilder entity(Entity entity) {
      this.entity = entity;
      return this;
    }

    public NetworkPlayerBuilder packetListener(PacketListener packetListener) {
      this.packetListener = packetListener;
      return this;
    }

    public NetworkPlayerBuilder serverPacketHandler(ServerPacketHandler serverPacketHandler) {
      this.serverPacketHandler = serverPacketHandler;
      return this;
    }

    public NetworkPlayer build() {
      return new NetworkPlayer(id, entity, packetListener, serverPacketHandler);
    }
  }
}
