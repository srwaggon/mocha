package mocha.shared;

import java.util.Comparator;

public class IdFactory<T extends Identified> {

  private Repository<T, Integer> repository;

  public IdFactory(Repository<T, Integer> repository) {
    this.repository = repository;
  }

  public Integer newId() {
    return repository.findAll().stream()
        .max(Comparator.comparing(T::getId))
        .map(Identified::getId)
        .orElse(0) + 1;
  }
}
