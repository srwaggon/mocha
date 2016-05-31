package mocha.game;

import javafx.scene.canvas.GraphicsContext;
import mocha.game.gfx.Renderable;
import mocha.game.world.Map;
import mocha.game.world.World;
import mocha.game.world.entity.Mob;

public class Game implements Renderable {

  private World world = new World();

  public Game() {
    addMaps();
    addEntities();
  }

  private void addEntities() {
    world.getMapById(1).addEntity(new Mob(0));
  }

  private void addMaps() {
    world.addMap(new Map(1, 10, 6));
  }

  public World getWorld() {
    return world;
  }

  @Override
  public void render(GraphicsContext graphics) {
    world.getMapById(1).render(graphics);
  }
}
