package mocha.game.rule;

import mocha.game.Game;
import mocha.game.world.Location;
import mocha.game.world.World;
import mocha.game.world.entity.Entity;
import mocha.game.world.map.Map;
import mocha.game.world.tile.Tile;

public class TransitionBetweenMapsRule implements GameRule {
  @Override
  public void apply(Game game) {
    World world = game.getWorld();
    game.getEntities().forEach(entity -> this.moveEntityBetweenMaps(world, entity));
  }

  private void moveEntityBetweenMaps(World world, Entity entity) {
    Location location = entity.getMovement().getLocation();
    Map map = world.getMapById(entity.getMapId());
    int mapWidth = map.getColumnCount() * Tile.SIZE;
    int mapHeight = map.getRowCount() * Tile.SIZE;

    handleNorth(world, entity, location, map);
    handleEast(world, entity, location, map, mapWidth);
    handleSouth(world, entity, location, map, mapHeight);
    handleWest(world, entity, location, map);
  }

  private void handleNorth(World world, Entity entity, Location entityLocation, Map currentMap) {
    if (entityLocation.getY() < 0) {
      currentMap.remove(entity);
      entityLocation.setY(world.getMapById(0).getRowCount() * Tile.SIZE - 1);
      world.getMapById(0).add(entity);
    }
  }

  private void handleEast(World world, Entity entity, Location entityLocation, Map currentMap, int currentMapWidth) {
    if (entityLocation.getX() >= currentMapWidth) {
      currentMap.remove(entity);
      entityLocation.setX(0);
      world.getMapById(1).add(entity);
    }
  }

  private void handleSouth(World world, Entity entity, Location entityLocation, Map currentMap, int currentMapHeight) {
    if (entityLocation.getY() >= currentMapHeight) {
      currentMap.remove(entity);
      entityLocation.setY(0);
      world.getMapById(2).add(entity);
    }
  }

  private void handleWest(World world, Entity entity, Location entityLocation, Map currentMap) {
    if (entityLocation.getX() < 0) {
      currentMap.remove(entity);
      entityLocation.setX(world.getMapById(3).getColumnCount() * Tile.SIZE - 1);
      world.getMapById(3).add(entity);
    }
  }
}
