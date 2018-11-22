package mocha.shared;

import java.util.List;
import java.util.Optional;

public interface Repository<T, ID> {

  List<T> findAll();

  T save(T entity);

  Optional<T> findById(ID id);

  void delete(T member);
}
