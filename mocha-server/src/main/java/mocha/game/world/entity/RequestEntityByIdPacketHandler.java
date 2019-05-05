package mocha.game.world.entity;

import com.google.common.eventbus.Subscribe;

import java.util.Optional;

import mocha.game.world.Location;
import mocha.net.packet.MochaConnection;
import mocha.net.packet.PacketHandler;

public class RequestEntityByIdPacketHandler implements PacketHandler<RequestEntityByIdPacket> {

  private MochaConnection mochaConnection;
  private EntityService entityService;

  public RequestEntityByIdPacketHandler(
      MochaConnection mochaConnection,
      EntityService entityService
  ) {
    this.mochaConnection = mochaConnection;
    this.entityService = entityService;
  }

  @Subscribe
  public void handle(RequestEntityByIdPacket requestEntityByIdPacket) {
    int entityId = requestEntityByIdPacket.getEntityId();
    Optional<Entity> optionalEntity = entityService.findById(entityId);
    if (optionalEntity.isPresent()) {
      mochaConnection.sendEntityUpdate(optionalEntity.get());
    } else {
      mochaConnection.sendEntityRemoved(new Entity(entityId, new Location()));
    }
  }
}