package mocha.game;

import mocha.game.world.entity.Foods;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import mocha.game.world.Map;
import mocha.game.world.World;
import mocha.game.world.entity.PlayerMob;
import mocha.gfx.Drawable;
import mocha.gfx.MochaCanvas;

@Component
public class Game implements Drawable {

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
    Foods newFoods = new Foods();
    world.getMapById(1).addEntity(newFoods);
  }

  private void addMaps() {
    world.addMap(new Map(1, 10, 6));
  }

  public World getWorld() {
    return world;
  }

  @Override
  public void draw(MochaCanvas mochaCanvas) {
    world.getMapById(1).draw(mochaCanvas);
  }

  void tick() {
    world.tick();
  }
}
