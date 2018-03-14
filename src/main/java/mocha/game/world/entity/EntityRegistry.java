package mocha.game.world.entity;

import com.google.common.collect.Maps;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class EntityRegistry {
  private Map<Integer, Entity> entities = Maps.newConcurrentMap();

  public Collection<Entity> getEntities() {
    return entities.values();
  }

  public Map<Integer, Entity> getEntitiesById() {
    return entities;
  }

  public Set<Integer> getIds() {
    return entities.keySet();
  }

  public void add(Entity entity) {
    entities.put(entity.getId(), entity);
  }

  public Optional<Entity> get(int id) {
    return Optional.ofNullable(entities.get(id));
  }
}
