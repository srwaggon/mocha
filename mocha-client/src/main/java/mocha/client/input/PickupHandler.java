package mocha.client.input;

import org.springframework.stereotype.Component;

import javax.inject.Inject;

import mocha.client.event.ClientEventBus;
import mocha.client.input.event.GameKeyEvent;
import mocha.game.player.PlayerService;
import mocha.game.world.item.PickUpItemPacket;

@Component
class PickupHandler implements GameKeyHandler {

  private PlayerService playerService;
  private ClientEventBus eventBus;

  @Inject
  public PickupHandler(
      PlayerService playerService,
      ClientEventBus eventBus
  ) {
    this.playerService = playerService;
    this.eventBus = eventBus;
  }

  @Override
  public void handle(GameKeyEvent gameKeyEvent) {
    if (gameKeyEvent.getGameKey().equals(GameKey.ACTION2)) {
      playerService.findPlayerEntity()
          .ifPresent(entity ->
              eventBus.postSendPacketEvent(new PickUpItemPacket(entity.getId())));
    }
  }
}
