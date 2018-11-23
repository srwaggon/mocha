package mocha.game.world.entity;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import mocha.shared.Repository;


@org.springframework.stereotype.Repository
public class ServerEntityToEntityRepositoryAdapter implements Repository<Entity, Integer> {

  @Inject
  private ServerEntityRepository serverEntityRepository;

  @Override
  public List<Entity> findAll() {
    return Lists.newArrayList(serverEntityRepository.findAll());
  }

  @Override
  public Entity save(Entity element) {
    ServerEntity toBeSaved = element instanceof ServerEntity ? (ServerEntity) element : new ServerEntity(element);
    return serverEntityRepository.save(toBeSaved);
  }

  @Override
  public Optional<Entity> findById(Integer id) {
    Optional<ServerEntity> maybeEntity = serverEntityRepository.findById(id);
    return maybeEntity.isPresent() ? Optional.of(maybeEntity.get()) : Optional.empty();
  }

  @Override
  public void delete(Entity element) {
    serverEntityRepository.findById(element.getId())
        .ifPresent(serverEntityRepository::delete);
  }
}
