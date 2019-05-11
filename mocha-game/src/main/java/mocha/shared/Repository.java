package mocha.shared;

import java.util.List;
import java.util.Optional;

public interface Repository<T extends Identified<ID>, ID> {

  List<T> findAll();

  T save(T element);

  Optional<T> findById(ID id);

  void delete(T element);

  void deleteAll();
}
