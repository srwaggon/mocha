package mocha.game.world.entity.movement.collision;

import mocha.game.Game;
import mocha.game.world.Location;
import mocha.game.world.entity.Entity;

public class EntityCollision extends SimpleCollision {

  private Game game;
  private Entity entity;
  private int width;
  private int height;

  public EntityCollision(Game game, Entity entity, int width, int height) {
    this.game = game;
    this.entity = entity;
    this.width = width;
    this.height = height;
  }

  @Override
  public boolean collides(Location location) {
    return game.getWorld().getChunkAt(entity.getLocation().getChunkIndex())
        .map(chunk -> chunk
            .getEntitiesAt(entity.getLocation()).stream()
            .filter(this::isNotSelf)
            .anyMatch(entity -> new RectangularCollision(entity.getLocation(), width, height).collides(location)))
        .orElse(false);

  }

  private boolean isNotSelf(Entity entity) {
    return entity.getId() != this.entity.getId();
  }
}
