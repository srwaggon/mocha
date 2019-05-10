package mocha.client.input;

import org.springframework.stereotype.Component;

import java.util.Optional;

import javax.inject.Inject;

import mocha.client.event.ClientEventBus;
import mocha.client.input.event.GameKeyEvent;
import mocha.game.player.PlayerService;
import mocha.game.world.entity.Entity;
import mocha.game.world.entity.movement.MovePacket;
import mocha.game.world.entity.movement.Movement;
import mocha.game.world.entity.movement.command.EntityMoveCommand;
import mocha.game.world.entity.movement.command.EntityMoveCommandFactory;
import mocha.shared.Repository;

@Component
class StopMoveHandler implements GameKeyHandler {

  private ClientEventBus clientEventBus;
  private PlayerService playerService;
  private Repository<Movement, Integer> movementRepository;

  @Inject
  public StopMoveHandler(PlayerService playerService, ClientEventBus clientEventBus, Repository<Movement, Integer> movementRepository) {
    this.playerService = playerService;
    this.clientEventBus = clientEventBus;
    this.movementRepository = movementRepository;
  }

  @Override
  public void handle(GameKeyEvent gameKeyEvent) {
    if (!gameKeyEvent.isDown()) {
      GameKey.getDirection(gameKeyEvent)
          .ifPresent(direction -> {
            Optional<Entity> playerEntity = playerService.findClientPlayerEntity();

            playerEntity.ifPresent(entity -> {
              EntityMoveCommand entityMoveCommand = EntityMoveCommandFactory.buildEntityMoveCommand(entity, direction, false);

              movementRepository.findById(entity.getId())
                  .ifPresent(movement -> movement.handle(entityMoveCommand));

              MovePacket movePacket = new MovePacket(entityMoveCommand);
              clientEventBus.postSendPacketEvent(movePacket);
            });
          });
    }
  }

}
