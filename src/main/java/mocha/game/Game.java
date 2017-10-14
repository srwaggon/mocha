package mocha.game;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import mocha.game.world.World;
import mocha.game.world.entity.EntityFactory;
import mocha.game.world.entity.Mob;
import mocha.game.world.map.Map;

@Component
@AllArgsConstructor
@NoArgsConstructor
public class Game {

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
    Mob player = entityFactory.createPlayer();
    world.getMapById(0).addEntity(player);
  }

  private void addMaps() {
    world.addMap(new Map(0, 18, 12));
    world.addMap(new Map(1, 18, 12));
    world.addMap(new Map(2, 18, 12));
    world.addMap(new Map(3, 18, 12));
  }

  public World getWorld() {
    return world;
  }

  void tick() {
    world.tick();
  }
}
