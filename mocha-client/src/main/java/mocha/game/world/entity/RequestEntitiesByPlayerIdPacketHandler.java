package mocha.game.world.entity;

import com.google.common.eventbus.Subscribe;

import org.springframework.stereotype.Component;

import javax.inject.Inject;

import mocha.game.player.PlayerService;
import mocha.net.packet.PacketHandler;

@Component
public class RequestEntitiesByPlayerIdPacketHandler implements PacketHandler {

  private PlayerService playerService;
  private EntityService entityService;

  @Inject
  public RequestEntitiesByPlayerIdPacketHandler(PlayerService playerService, EntityService entityService) {
    this.playerService = playerService;
    this.entityService = entityService;
  }

  @Subscribe
  public void handle(RequestEntitiesByPlayerIdPacket requestEntitiesByPlayerIdPacket) {
    playerService.findById(requestEntitiesByPlayerIdPacket.getPlayerId())
        .ifPresent(player -> requestEntitiesByPlayerIdPacket.getEntityIds()
            .forEach(entityId -> entityService.findById(entityId)
                .ifPresent(player::setEntity)));
  }
}