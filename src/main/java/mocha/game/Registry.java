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

  public Map<Integer, T> getByid() {
    return members;
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

  public T remove(T member) {
    return members.remove(member.getId());
  }
}
