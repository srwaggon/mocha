package mocha.game.world.entity;

public class EntityIdFactory {

  private static int last = 0;
  private EntityRegistry entityRegistry;

  public EntityIdFactory(EntityRegistry entityRegistry) {
    this.entityRegistry = entityRegistry;
  }

  public int newEntityId() {
    while (entityRegistry.getIds().contains(last)) {
      last++;
    }
    return last;
  }
}
