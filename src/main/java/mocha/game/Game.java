package mocha.game;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.stream.Collectors;

import mocha.game.rule.GameRule;
import mocha.game.world.Location;
import mocha.game.world.World;
import mocha.game.world.chunk.Chunk;
import mocha.game.world.entity.Entity;
import mocha.game.world.entity.EntityRegistry;

public class Game implements Tickable {

  private World world;
  private List<GameRule> gameRules;
  protected EntityRegistry entityRegistry;

  public Game(World world, List<GameRule> gameRules, EntityRegistry entityRegistry) {
    this.world = world;
    this.gameRules = gameRules;
    this.entityRegistry = entityRegistry;
  }

  public void tick(long now) {
    gameRules.forEach(gameRule -> gameRule.apply(this));
  }

  public World getWorld() {
    return world;
  }

  public void add(Entity entity) {
    entityRegistry.add(entity);
    world.add(entity);
  }

  private List<Chunk> getActiveChunks() {
    Location playerLocation = Location.at(0,0);
    List<Chunk> activeChunks = Lists.newLinkedList();
    for (int y = -1; y <= 1; y++) {
      for (int x = -1; x <= 1; x++) {
        Location chunkLocation = playerLocation.add(x * Chunk.getWidth(), y * Chunk.getHeight());
        world.getChunkAt(chunkLocation).ifPresent(activeChunks::add);
      }
    }
    return activeChunks;
  }

  public List<Entity> getActiveEntities() {
    return world.getChunks().stream()
        .map(Chunk::getEntities)
        .flatMap(List::stream)
        .collect(Collectors.toList());
  }
}
