package mocha.game;

import mocha.game.world.entity.Entity;
import mocha.net.packet.MochaConnection;
import mocha.net.packet.PacketListener;
import mocha.server.ServerPacketHandler;

public class NetworkPlayer implements Player {

  private int id;
  private Entity entity;
  private MochaConnection mochaConnection;
  private PacketListener packetListener;
  private ServerPacketHandler serverPacketHandler;

  private NetworkPlayer(int id, Entity entity, MochaConnection mochaConnection, PacketListener packetListener, ServerPacketHandler serverPacketHandler) {
    this.id = id;
    this.entity = entity;
    this.mochaConnection = mochaConnection;
    this.packetListener = packetListener;
    this.serverPacketHandler = serverPacketHandler;
  }

  public void remove() {
    mochaConnection.disconnect();
    packetListener.remove();
    serverPacketHandler.remove();
  }

  @Override
  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public Entity getEntity() {
    return entity;
  }

  public void setEntity(Entity entity) {
    this.entity = entity;
  }

  public static NetworkPlayerBuilder builder() {
    return new NetworkPlayerBuilder();
  }

  public static class NetworkPlayerBuilder {
    private int id;
    private Entity entity;
    private MochaConnection mochaConnection;
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

    public NetworkPlayerBuilder mochaConnection(MochaConnection mochaConnection) {
      this.mochaConnection = mochaConnection;
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
      return new NetworkPlayer(id, entity, mochaConnection, packetListener, serverPacketHandler);
    }
  }
}
