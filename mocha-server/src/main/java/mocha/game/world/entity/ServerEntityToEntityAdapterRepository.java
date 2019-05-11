package mocha.game.world.entity;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import mocha.shared.Repository;

import static java.util.stream.Collectors.toList;


@org.springframework.stereotype.Repository
public class ServerEntityToEntityAdapterRepository implements Repository<Entity, Integer> {

  private ServerEntityRepository serverEntityRepository;
  private EntityFactory entityFactory;

  @Inject
  public ServerEntityToEntityAdapterRepository(
      ServerEntityRepository serverEntityRepository,
      EntityFactory entityFactory
  ) {
    this.serverEntityRepository = serverEntityRepository;
    this.entityFactory = entityFactory;
  }

  @Override
  public List<Entity> findAll() {
    return serverEntityRepository.findAll().stream()
        .map(this::getEntityOfRightType)
        .collect(toList());
  }

  @Override
  public Entity save(Entity entity) {
    ServerEntity toBeSaved = entity instanceof ServerEntity ? (ServerEntity) entity : new ServerEntity(entity);
    serverEntityRepository.save(toBeSaved);
    return entity;
  }

  @Override
  public Optional<Entity> findById(Integer id) {
    Optional<ServerEntity> byId = serverEntityRepository.findById(id);
    if (!byId.isPresent()) {
      return Optional.empty();
    }

    ServerEntity serverEntity = byId.get();

    return Optional.of(getEntityOfRightType(serverEntity));

  }

  private Entity getEntityOfRightType(ServerEntity serverEntity) {
    return EntityType.ITEM.equals(serverEntity.getEntityType()) ? entityFactory.newItemEntity(serverEntity) : serverEntity;
  }

  @Override
  public void delete(Entity entity) {
    serverEntityRepository.findById(entity.getId())
        .ifPresent(serverEntityRepository::delete);
  }

  @Override
  public void deleteAll() {
    serverEntityRepository.deleteAll();
  }
}
