package mocha.game.world.entity;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.inject.Inject;

import mocha.game.world.entity.movement.MovementFactory;
import mocha.shared.Repository;


@org.springframework.stereotype.Repository
public class ServerEntityToEntityRepositoryAdapter implements Repository<Entity, Integer> {

  @Inject
  private ServerEntityRepository serverEntityRepository;

  @Inject
  private MovementFactory movementFactory;

  @Override
  public List<Entity> findAll() {
    return Lists.newArrayList(serverEntityRepository.findAll()).stream()
        .peek(this::addMovement)
        .collect(Collectors.toList());
  }

  @Override
  public Entity save(Entity entity) {
    ServerEntity toBeSaved = entity instanceof ServerEntity ? (ServerEntity) entity : new ServerEntity(entity);
    ServerEntity saved = serverEntityRepository.save(toBeSaved);
    addMovement(saved);
    return saved;
  }

  private void addMovement(ServerEntity saved) {
    saved.setMovement(movementFactory.newSlidingMovement(saved));
  }

  @Override
  public Optional<Entity> findById(Integer id) {
    Optional<ServerEntity> maybeEntity = serverEntityRepository.findById(id);
    if (maybeEntity.isPresent()) {
      ServerEntity entity = maybeEntity.get();
      addMovement(entity);
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
