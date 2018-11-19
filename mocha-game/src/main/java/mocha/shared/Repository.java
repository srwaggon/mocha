package mocha.shared;

import java.util.Collection;
import java.util.Optional;

public interface Repository<T, ID> {

  Collection<T> findAll();

  void save(T entity);

  Optional<T> findById(ID id);

  boolean containsKey(ID key);

  void delete(T member);
}
