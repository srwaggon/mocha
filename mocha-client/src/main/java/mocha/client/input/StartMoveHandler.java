package mocha.client.input;

import org.springframework.stereotype.Component;

import javax.inject.Inject;

import mocha.client.input.event.GameKeyEvent;
import mocha.game.ClientGameLogic;
import mocha.game.player.PlayerService;
import mocha.game.world.entity.movement.command.EntityMoveCommandFactory;

@Component
class StartMoveHandler implements GameKeyHandler {

  private PlayerService playerService;
  private ClientGameLogic gameLogic;

  @Inject
  public StartMoveHandler(
      PlayerService playerService,
      ClientGameLogic gameLogic
  ) {
    this.playerService = playerService;
    this.gameLogic = gameLogic;
  }

  @Override
  public void handle(GameKeyEvent gameKeyEvent) {
    if (gameKeyEvent.isDown()) {
      GameKey.getDirection(gameKeyEvent)
          .ifPresent(direction ->
              playerService.findPlayerEntity().ifPresent(entity ->
                  gameLogic.movePlayerEntity(EntityMoveCommandFactory.buildEntityStartMoveCommand(entity, direction))));
    }
  }
}
