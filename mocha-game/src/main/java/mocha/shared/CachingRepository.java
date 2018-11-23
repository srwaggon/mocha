package mocha.shared;

import com.google.common.collect.Maps;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class CachingRepository<T extends Identified<ID>, ID> implements Repository<T, ID> {

  private Map<ID, T> members = Maps.newConcurrentMap();
  private Repository<T, ID> repository;

  public CachingRepository(Repository<T, ID> repository) {
    this.repository = repository;
  }

  @Override
  public List<T> findAll() {
    List<T> all = repository.findAll();
    all.forEach(this::cache);
    return all;
  }

  @Override
  public T save(T t) {
    T member = repository.save(t);
    cache(member);
    return member;
  }

  private void cache(T result) {
    members.put(result.getId(), result);
  }

  @Override
  public Optional<T> findById(ID id) {
    Optional<T> member = Optional.ofNullable(members.get(id));
    if (member.isPresent()) {
      return member;
    }
    Optional<T> t = repository.findById(id);
    t.ifPresent(this::cache);
    return t;
  }

  @Override
  public void delete(T member) {
    repository.delete(member);
    members.remove(member.getId());
  }
}
