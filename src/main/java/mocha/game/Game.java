package mocha.game;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import mocha.game.world.entity.EntityFactory;
import mocha.game.world.map.Map;
import mocha.game.world.World;
import mocha.game.world.entity.PlayerMob;
import mocha.gfx.Drawable;
import mocha.gfx.MochaCanvas;

@Component
@AllArgsConstructor
@NoArgsConstructor
public class Game implements Drawable {

  @Inject
  private World world;
  @Inject
  private EntityFactory entityFactory;

  @PostConstruct
  void init() {
    addMaps();
    addEntities();
  }

  private void addEntities() {
    PlayerMob player = entityFactory.createPlayer();
    world.getMapById(1).addEntity(player);
  }

  private void addMaps() {
    world.addMap(new Map(1, 10, 6));
    world.addMap(new Map(2, 10, 6));
  }

  public World getWorld() {
    return world;
  }

  @Override
  public void draw(MochaCanvas mochaCanvas, int x, int y) {
    world.getMapById(1).draw(mochaCanvas, -1, -1);
  }

  void tick() {
    world.tick();
  }
}
