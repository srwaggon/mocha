package mocha.game;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import lombok.Data;
import lombok.NoArgsConstructor;
import mocha.game.world.Location;
import mocha.game.world.World;
import mocha.game.world.entity.EntityFactory;
import mocha.game.world.entity.Mob;
import mocha.game.world.map.Map;
import mocha.game.world.tile.Tile;

@Data
@Component
@NoArgsConstructor
public class Game {

  @Inject
  private World world;
  @Inject
  private EntityFactory entityFactory;

  private Mob player;

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
    player = entityFactory.createPlayer();
    world.getMapById(0).add(player);
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

    Location playerLocation = player.getMovementComponent().getLocation();

    Map currentMap = world.getMapById(player.getMapId());
    int currentMapWidth = currentMap.getColumnCount() * Tile.SIZE;
    int currentMapHeight = currentMap.getRowCount() * Tile.SIZE;

    if (playerLocation.getX() >= currentMapWidth) {
      currentMap.remove(player);
      playerLocation.setX(0);
      playerLocation.setY(playerLocation.getY());
      Map nextMap = world.getMapById(1);
      nextMap.add(player);
    }

    if (playerLocation.getY() >= currentMapHeight) {
      currentMap.remove(player);
      Map nextMap = world.getMapById(2);
      playerLocation.setX(playerLocation.getX());
      playerLocation.setY(0);
      nextMap.add(player);
    }

    if (playerLocation.getX() < 0) {
      currentMap.remove(player);
      Map nextMap = world.getMapById(3);
      playerLocation.setX(nextMap.getColumnCount() * Tile.SIZE - 1);
      playerLocation.setY(0);
      nextMap.add(player);
    }

    if (playerLocation.getY() < 0) {
      currentMap.remove(player);
      Map nextMap = world.getMapById(0);
      playerLocation.setX(playerLocation.getX());
      playerLocation.setY(nextMap.getRowCount() * Tile.SIZE - 1);
      nextMap.add(player);
    }

  }
}
