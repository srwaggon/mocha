package mocha.game;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import javafx.scene.canvas.GraphicsContext;
import mocha.game.gfx.Renderable;
import mocha.game.world.Map;
import mocha.game.world.World;
import mocha.game.world.entity.PlayerMob;

@Component
public class Game implements Renderable {

  @Inject
  private World world;
  @Inject
  private PlayerMob player;

  @PostConstruct
  public void init() {
    addMaps();
    addEntities();
  }

  public Game() {

  }

  public Game(World world, PlayerMob player) {
    this.world = world;
    this.player = player;
  }

  private void addEntities() {
    world.getMapById(1).addEntity(player);
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

  void tick() {
    world.tick();
  }
}
