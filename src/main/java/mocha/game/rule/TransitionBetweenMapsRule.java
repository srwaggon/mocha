package mocha.game.rule;

import mocha.game.Game;
import mocha.game.world.Location;
import mocha.game.world.World;
import mocha.game.world.entity.Entity;
import mocha.game.world.chunk.Chunk;
import mocha.game.world.tile.Tile;

public class TransitionBetweenMapsRule implements GameRule {
  @Override
  public void apply(Game game) {
    World world = game.getWorld();
    game.getEntities().forEach(entity -> this.moveEntityBetweenMaps(world, entity));
  }

  private void moveEntityBetweenMaps(World world, Entity entity) {
    Location location = entity.getMovement().getLocation();
    Chunk chunk = world.getMapById(entity.getChunkId());
    int mapWidth = chunk.getColumnCount() * Tile.SIZE;
    int mapHeight = chunk.getRowCount() * Tile.SIZE;

    handleNorth(world, entity, location, chunk);
    handleEast(world, entity, location, chunk, mapWidth);
    handleSouth(world, entity, location, chunk, mapHeight);
    handleWest(world, entity, location, chunk);
  }

  private void handleNorth(World world, Entity entity, Location entityLocation, Chunk currentChunk) {
    if (entityLocation.getY() < 0) {
      currentChunk.remove(entity);
      entityLocation.setY(world.getMapById(0).getRowCount() * Tile.SIZE - 1);
      world.getMapById(0).add(entity);
    }
  }

  private void handleEast(World world, Entity entity, Location entityLocation, Chunk currentChunk, int currentMapWidth) {
    if (entityLocation.getX() >= currentMapWidth) {
      currentChunk.remove(entity);
      entityLocation.setX(0);
      world.getMapById(1).add(entity);
    }
  }

  private void handleSouth(World world, Entity entity, Location entityLocation, Chunk currentChunk, int currentMapHeight) {
    if (entityLocation.getY() >= currentMapHeight) {
      currentChunk.remove(entity);
      entityLocation.setY(0);
      world.getMapById(2).add(entity);
    }
  }

  private void handleWest(World world, Entity entity, Location entityLocation, Chunk currentChunk) {
    if (entityLocation.getX() < 0) {
      currentChunk.remove(entity);
      entityLocation.setX(world.getMapById(3).getColumnCount() * Tile.SIZE - 1);
      world.getMapById(3).add(entity);
    }
  }
}
