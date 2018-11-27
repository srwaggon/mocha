package mocha.game.world.entity;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import mocha.shared.Repository;


@org.springframework.stereotype.Repository
public class ServerEntityToEntityRepositoryAdapter implements Repository<Entity, Integer> {

  private ServerEntityRepository serverEntityRepository;

  @Inject
  public ServerEntityToEntityRepositoryAdapter(
      ServerEntityRepository serverEntityRepository
  ) {
    this.serverEntityRepository = serverEntityRepository;
  }

  @Override
  public List<Entity> findAll() {
    return Lists.newArrayList(serverEntityRepository.findAll());
  }

  @Override
  public Entity save(Entity entity) {
    ServerEntity toBeSaved = entity instanceof ServerEntity ? (ServerEntity) entity : new ServerEntity(entity);
    serverEntityRepository.save(toBeSaved);
    return entity;
  }

  @Override
  public Optional<Entity> findById(Integer id) {
    Optional<ServerEntity> maybeEntity = serverEntityRepository.findById(id);
    return maybeEntity.isPresent() ? Optional.of(maybeEntity.get()) : Optional.empty();
  }

  @Override
  public void delete(Entity entity) {
    serverEntityRepository.findById(entity.getId())
        .ifPresent(serverEntityRepository::delete);
  }
}
