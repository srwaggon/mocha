package mocha.shared;

import java.util.Collection;
import java.util.Optional;

public interface Repository<T, ID> {

  Collection<T> findAll();

  T save(T entity);

  Optional<T> findById(ID id);

  void delete(T member);
}
