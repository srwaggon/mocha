package mocha.shared;

public class IdFactory<T extends Identified> {

  private int highest = 0;
  private Repository<T, Integer> repository;

  public IdFactory(Repository<T, Integer> repository) {
    this.repository = repository;
  }

  public Integer newId() {
    for (int newId = 0; newId < highest; newId++) {
      if (!repository.findById(newId).isPresent()) {
        return newId;
      }
    }
    return highest++;
  }
}
