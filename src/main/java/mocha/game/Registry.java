package mocha.game;

import com.google.common.collect.Maps;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class Registry<T extends Identified> {
  private Map<Integer, T> members = Maps.newConcurrentMap();

  public Collection<T> getMembers() {
    return members.values();
  }

  public Set<Integer> getIds() {
    return members.keySet();
  }

  public void add(T entity) {
    members.put(entity.getId(), entity);
  }

  public Optional<T> get(int id) {
    return Optional.ofNullable(members.get(id));
  }

  public boolean containsKey(Integer key) {
    return members.containsKey(key);
  }
  public boolean containsValue(T value) {
    return members.containsValue(value);
  }

  public T remove(T member) {
    return members.remove(member.getId());
  }
}
