package mocha.game;

import com.google.common.collect.Lists;

import org.springframework.stereotype.Component;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import lombok.Data;
import lombok.NoArgsConstructor;
import mocha.game.world.Location;
import mocha.game.world.World;
import mocha.game.world.entity.Entity;
import mocha.game.world.entity.EntityFactory;
import mocha.game.world.entity.brain.InputBrain;
import mocha.game.world.map.Map;
import mocha.game.world.map.MapFactory;
import mocha.game.world.tile.Tile;

@Data
@Component
@NoArgsConstructor
public class Game implements Tickable {

  @Inject
  private World world;
  @Inject
  private EntityFactory entityFactory;
  @Inject
  private MapFactory mapFactory;

  private Entity player;

  private List<Entity> entities = Lists.newArrayList();

  public Game(World world, EntityFactory entityFactory) {
    this.world = world;
    this.entityFactory = entityFactory;
  }

  @PostConstruct
  void init() {
    addMaps();
    addEntities();
  }

  private void addEntities() {
    addPlayer(entityFactory.createRandom());

    addNpcs();
  }

  private void addNpcs() {
    for (int i = 0; i < 10; i++) {
      addEntity(entityFactory.createRandom());
      addEntity(entityFactory.createRandomSlider());
      addEntity(entityFactory.createRandomAccelerating());
    }
  }

  private void addPlayer(Entity entity) {
    this.player = entity;
    entity.setBrain(new InputBrain(entity));
    addEntity(entity);
  }

  private void addEntity(Entity entity) {
    entities.add(entity);
    world.getMapById(0).add(entity);
  }

  private void addMaps() {
    world.addMap(mapFactory.newRandomDefault());
    world.addMap(mapFactory.newRandomDefault());
    world.addMap(mapFactory.newRandomDefault());
    world.addMap(mapFactory.newRandomDefault());
  }

  public World getWorld() {
    return world;
  }

  public void tick(long now) {
    world.tick(now);
    getEntities().forEach(this::moveEntityBetweenMaps);
  }

  private void moveEntityBetweenMaps(Entity entity) {
    Location entityLocation = entity.getMovement().getLocation();
    Map currentMap = world.getMapById(entity.getMapId());
    int currentMapWidth = currentMap.getColumnCount() * Tile.SIZE;
    int currentMapHeight = currentMap.getRowCount() * Tile.SIZE;

    if (entityLocation.getX() >= currentMapWidth) {
      currentMap.remove(entity);
      entityLocation.setX(0);
      world.getMapById(1).add(entity);
    }

    if (entityLocation.getY() >= currentMapHeight) {
      currentMap.remove(entity);
      entityLocation.setY(0);
      world.getMapById(2).add(entity);
    }

    if (entityLocation.getX() < 0) {
      currentMap.remove(entity);
      entityLocation.setX(world.getMapById(3).getColumnCount() * Tile.SIZE - 1);
      world.getMapById(3).add(entity);
    }

    if (entityLocation.getY() < 0) {
      currentMap.remove(entity);
      entityLocation.setY(world.getMapById(0).getRowCount() * Tile.SIZE - 1);
      world.getMapById(0).add(entity);
    }
  }
}
