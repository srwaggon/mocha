package mocha.game.world.entity;

import com.google.common.eventbus.Subscribe;

import org.springframework.stereotype.Component;

import javax.inject.Inject;

import mocha.game.event.MochaEventHandler;
import mocha.game.world.entity.event.EntityUpdatedEvent;
import mocha.game.world.entity.movement.Movement;
import mocha.game.world.entity.movement.MovementFactory;
import mocha.shared.Repository;

@Component
public class EntityUpdatedEventHandler implements MochaEventHandler<EntityUpdatedEvent> {

  private MovementFactory movementFactory;
  private EntityService entityService;
  private Repository<Movement, Integer> movementRepository;
  private Repository<Entity, Integer> entityRepository;

  @Inject
  public EntityUpdatedEventHandler(
      MovementFactory movementFactory,
      EntityService entityService,
      Repository<Movement, Integer> movementRepository,
      Repository<Entity, Integer> entityRepository
  ) {
    this.movementFactory = movementFactory;
    this.entityService = entityService;
    this.movementRepository = movementRepository;
    this.entityRepository = entityRepository;
  }

  @Subscribe
  public void handle(EntityUpdatedEvent entityUpdatedEvent) {
    Entity entityUpdate = entityUpdatedEvent.getEntity();
    createEntityIfAbsent(entityUpdate);
    updateEntity(entityUpdate);
  }

  private void updateEntity(Entity entityUpdate) {
    entityRepository.findById(entityUpdate.getId())
        .ifPresent(entity ->
            entity.getLocation().set(entityUpdate.getLocation()));
  }

  private void createEntityIfAbsent(Entity entity) {
    if (!entityRepository.findById(entity.getId()).isPresent()) {
      Entity resultEntity = entityService.save(entity);
      movementRepository.save(movementFactory.newSlidingMovement(resultEntity));
    }
  }
}