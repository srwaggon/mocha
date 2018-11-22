package mocha.shared;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class InMemoryRepository<T extends Identified> implements Repository<T, Integer> {
  private Map<Integer, T> members = Maps.newConcurrentMap();

  @Override
  public List<T> findAll() {
    return Lists.newArrayList(members.values());
  }

  @Override
  public T save(T entity) {
    members.put(entity.getId(), entity);
    return entity;
  }

  @Override
  public Optional<T> findById(Integer id) {
    return Optional.ofNullable(members.get(id));
  }

  @Override
  public void delete(T member) {
    members.remove(member.getId());
  }
}
