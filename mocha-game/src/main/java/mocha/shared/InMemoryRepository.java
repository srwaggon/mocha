package mocha.shared;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class InMemoryRepository<T extends Identified<Integer>> implements Repository<T, Integer> {

  private Map<Integer, T> members = Maps.newConcurrentMap();

  @Override
  public List<T> findAll() {
    return Lists.newArrayList(members.values());
  }

  @Override
  public T save(T element) {
    members.put(element.getId(), element);
    return element;
  }

  @Override
  public Optional<T> findById(Integer id) {
    return Optional.ofNullable(members.get(id));
  }

  @Override
  public void delete(T element) {
    members.remove(element.getId());
  }
}
