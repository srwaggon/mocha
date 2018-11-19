package mocha.shared;

import com.google.common.collect.Maps;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

public class InMemoryRepository<T extends Identified> implements Repository<T, Integer> {
  private Map<Integer, T> members = Maps.newConcurrentMap();

  @Override
  public Collection<T> findAll() {
    return members.values();
  }

  @Override
  public void save(T entity) {
    members.put(entity.getId(), entity);
  }

  @Override
  public Optional<T> findById(Integer id) {
    return Optional.ofNullable(members.get(id));
  }

  @Override
  public boolean containsKey(Integer key) {
    return members.containsKey(key);
  }

  @Override
  public void delete(T member) {
    members.remove(member.getId());
  }
}
