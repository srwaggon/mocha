package mocha.game.world.entity;

import com.google.common.collect.Lists;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import mocha.shared.Repository;

@Component
public class ServerEntityToEntityRepositoryAdapter implements Repository<Entity, Integer> {

  @Inject
  private ServerEntityRepository serverEntityRepository;

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
    return maybeEntity.isPresent() ? Optional.of(maybeEntity.get()) : Optional.empty();
  }

  @Override
  public void delete(Entity member) {
    serverEntityRepository.findById(member.getId())
        .ifPresent(serverEntityRepository::delete);
  }
}
