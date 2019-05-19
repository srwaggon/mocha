package mocha.game.world.entity;

import org.springframework.context.annotation.Lazy;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import mocha.game.world.collision.CollisionFactory;
import mocha.game.world.item.Item;
import mocha.game.world.item.ItemService;
import mocha.shared.Repository;

import static java.util.stream.Collectors.toList;


@org.springframework.stereotype.Repository
public class ServerEntityToEntityAdapterRepository implements Repository<Entity, Integer> {

  private ServerEntityRepository serverEntityRepository;
  private ItemService itemService;
  private CollisionFactory collisionFactory;

  @Inject
  public ServerEntityToEntityAdapterRepository(
      ServerEntityRepository serverEntityRepository,
      ItemService itemService,
      @Lazy CollisionFactory collisionFactory
  ) {
    this.serverEntityRepository = serverEntityRepository;
    this.itemService = itemService;
    this.collisionFactory = collisionFactory;
  }

  @Override
  public List<Entity> findAll() {
    return serverEntityRepository.findAll().stream()
        .map(this::getEntityOfRightType)
        .collect(toList());
  }

  @Override
  public Entity save(Entity entity) {
    return serverEntityRepository.save(new ServerEntity(entity));
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
    if (EntityType.ITEM.equals(serverEntity.getEntityType())) {
      Item item = itemService.findById(serverEntity.getTypeId());
      return new ItemEntity(serverEntity.getId(), serverEntity.getLocation(), item);
    } else {
      // TODO: remove (hack)
      serverEntity.setCollision(collisionFactory.newEntityHitBoxCollision(serverEntity));
      return serverEntity;
    }
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
