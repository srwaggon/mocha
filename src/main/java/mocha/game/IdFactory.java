package mocha.game;

public class IdFactory<T extends Identified> {

  private int last = 0;
  private Registry registry;

  public IdFactory(Registry<T> registry) {
    this.registry = registry;
  }

  public int newId() {
    while (registry.getIds().contains(last)) {
      last++;
    }
    return last;
  }
}
