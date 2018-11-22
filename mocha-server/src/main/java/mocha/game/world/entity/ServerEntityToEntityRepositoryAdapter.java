package mocha.game.world.entity;

import com.google.common.collect.Lists;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import mocha.game.world.entity.movement.MovementFactory;
import mocha.shared.Repository;

@Component
public class ServerEntityToEntityRepositoryAdapter implements Repository<Entity, Integer> {

  @Inject
  private ServerEntityRepository serverEntityRepository;

  @Inject
  private MovementFactory movementFactory;

  @Override
  public List<Entity> findAll() {
    return Lists.newArrayList(serverEntityRepository.findAll());
  }

  @Override
  public Entity save(Entity entity) {
    ServerEntity serverEntity = entity instanceof ServerEntity ? (ServerEntity) entity : new ServerEntity(entity);
    return serverEntityRepository.save(serverEntity);
  }

  @Override
  public Optional<Entity> findById(Integer id) {
    Optional<ServerEntity> maybeEntity = serverEntityRepository.findById(id);
    if (maybeEntity.isPresent()) {
      ServerEntity entity = maybeEntity.get();
      entity.setMovement(movementFactory.newSlidingMovement(entity));
      return Optional.of(entity);
    } else {
      return Optional.empty();
    }
  }

  @Override
  public void delete(Entity member) {
    serverEntityRepository.findById(member.getId())
        .ifPresent(serverEntityRepository::delete);
  }
}
