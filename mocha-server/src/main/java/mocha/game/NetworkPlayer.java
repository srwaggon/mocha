package mocha.game;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import mocha.game.player.Player;
import mocha.game.world.entity.Entity;
import mocha.net.packet.PacketListener;
import mocha.server.ServerPacketResolver;

@javax.persistence.Entity
public class NetworkPlayer implements Player {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int id;
  @Transient
  private Entity entity;
  @Transient
  private PacketListener packetListener;
  @Transient
  private ServerPacketResolver serverPacketHandler;

  private NetworkPlayer(
      int id,
      Entity entity,
      PacketListener packetListener,
      ServerPacketResolver serverPacketHandler
  ) {
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
  public Integer getId() {
    return id;
  }

  @Override
  public void setId(Integer id) {
    this.id = id;
  }

  @Override
  public Entity getEntity() {
    return entity;
  }

  @Override
  public void setEntity(Entity entity) {
    this.entity = entity;
  }

  static NetworkPlayerBuilder builder() {
    return new NetworkPlayerBuilder();
  }

  public static class NetworkPlayerBuilder {
    private int id;
    private Entity entity;
    private PacketListener packetListener;
    private ServerPacketResolver serverPacketHandler;

    NetworkPlayerBuilder id(int id) {
      this.id = id;
      return this;
    }

    public NetworkPlayerBuilder entity(Entity entity) {
      this.entity = entity;
      return this;
    }

    NetworkPlayerBuilder packetListener(PacketListener packetListener) {
      this.packetListener = packetListener;
      return this;
    }

    NetworkPlayerBuilder serverPacketHandler(ServerPacketResolver serverPacketHandler) {
      this.serverPacketHandler = serverPacketHandler;
      return this;
    }

    NetworkPlayer build() {
      return new NetworkPlayer(id, entity, packetListener, serverPacketHandler);
    }
  }
}
