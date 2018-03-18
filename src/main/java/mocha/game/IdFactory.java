package mocha.game;

public class IdFactory<T extends Identified> {

  private int highest = 0;
  private Registry registry;

  public IdFactory(Registry<T> registry) {
    this.registry = registry;
  }

  public int newId() {
    for (int newId = 0; newId < highest; newId++) {
      if (!registry.containsKey(newId)) {
        return newId;
      }
    }
    return highest++;
  }
}
