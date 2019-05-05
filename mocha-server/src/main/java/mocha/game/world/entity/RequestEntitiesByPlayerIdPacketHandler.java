package mocha.game.world.entity;

import com.google.common.eventbus.Subscribe;

import mocha.game.player.PlayerService;
import mocha.net.packet.MochaConnection;
import mocha.net.packet.PacketHandler;


public class RequestEntitiesByPlayerIdPacketHandler implements PacketHandler<RequestEntitiesByPlayerIdPacket> {

  private MochaConnection mochaConnection;
  private EntityService entityService;
  private PlayerService playerService;

  public RequestEntitiesByPlayerIdPacketHandler(
      MochaConnection mochaConnection,
      EntityService entityService, PlayerService playerService
  ) {
    this.mochaConnection = mochaConnection;
    this.entityService = entityService;
    this.playerService = playerService;
  }

  @Subscribe
  public void handle(RequestEntitiesByPlayerIdPacket requestEntitiesByPlayerIdPacket) {
    int playerId = requestEntitiesByPlayerIdPacket.getPlayerId();
    playerService.findById(playerId).ifPresent(player -> {
      int entityId = playerService.getEntityForPlayer(player).getId();
      entityService.findById(entityId).ifPresent(mochaConnection::sendEntityUpdate);
      mochaConnection.requestEntitiesByPlayerId(playerId, entityId);
    });
  }
}