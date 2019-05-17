package mocha.game.world.entity;

import com.google.common.eventbus.Subscribe;

import org.springframework.stereotype.Component;

import javax.inject.Inject;

import mocha.game.event.MochaEventHandler;
import mocha.game.world.collision.Collision;
import mocha.game.world.collision.CollisionFactory;
import mocha.game.world.entity.event.EntityUpdatedEvent;
import mocha.game.world.entity.movement.Movement;
import mocha.game.world.entity.movement.MovementFactory;
import mocha.shared.Repository;

@Component
public class EntityUpdatedEventHandler implements MochaEventHandler<EntityUpdatedEvent> {

  private MovementFactory movementFactory;
  private EntityService entityService;
  private Repository<Movement, Integer> movementRepository;
  private CollisionFactory collisionFactory;

  @Inject
  public EntityUpdatedEventHandler(
      MovementFactory movementFactory,
      EntityService entityService,
      Repository<Movement, Integer> movementRepository,
      CollisionFactory collisionFactory
  ) {
    this.movementFactory = movementFactory;
    this.entityService = entityService;
    this.movementRepository = movementRepository;
    this.collisionFactory = collisionFactory;
  }

  @Subscribe
  public void handle(EntityUpdatedEvent entityUpdatedEvent) {
    Entity entityUpdate = entityUpdatedEvent.getEntity();
    createEntityIfAbsent(entityUpdate);
    updateEntity(entityUpdate);
  }

  private void updateEntity(Entity entityUpdate) {
    entityService.findById(entityUpdate.getId())
        .ifPresent(entity ->
            entity.getLocation().set(entityUpdate.getLocation()));
  }

  private void createEntityIfAbsent(Entity entity) {
    if (!entityService.findById(entity.getId()).isPresent()) {
      Entity resultEntity = entityService.save(entity);
      Collision collision = collisionFactory.newEntityHitBoxCollision(resultEntity);
      resultEntity.setCollision(collision);
      movementRepository.save(movementFactory.newSlidingMovement(resultEntity));
    }
  }
}