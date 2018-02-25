package mocha.game;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.stream.Collectors;

import mocha.game.rule.GameRule;
import mocha.game.world.Location;
import mocha.game.world.World;
import mocha.game.world.chunk.Chunk;
import mocha.game.world.entity.Entity;

public class Game implements Tickable {

  private World world;
  private Entity player;
  private List<GameRule> gameRules;

  public Game(World world, List<GameRule> gameRules) {
    this.world = world;
    this.gameRules = gameRules;
  }

  public void tick(long now) {
    gameRules.forEach(gameRule -> gameRule.apply(this));
  }

  public void setPlayer(Entity player) {
    this.player = player;
  }

  public Entity getPlayer() {
    return player;
  }

  public World getWorld() {
    return world;
  }

  public void add(Entity entity) {
    world.add(entity);
  }

  private List<Chunk> getActiveChunks() {
    Location playerLocation = getPlayer().getMovement().getLocation();
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
