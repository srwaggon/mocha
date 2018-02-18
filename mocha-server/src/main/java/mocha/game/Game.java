package mocha.game;

import com.google.common.collect.Lists;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import lombok.Data;
import lombok.NoArgsConstructor;
import mocha.game.rule.GameRule;
import mocha.game.world.Location;
import mocha.game.world.World;
import mocha.game.world.chunk.Chunk;
import mocha.game.world.entity.Entity;
import mocha.game.world.entity.EntityFactory;

@Data
@Component
@NoArgsConstructor
public class Game implements Tickable {

  @Inject
  private World world;
  @Inject
  @Qualifier("player")
  private Entity player;
  @Inject
  private List<GameRule> gameRules;

  @Inject
  private EntityFactory entityFactory;

  public void tick(long now) {
    gameRules.forEach(gameRule -> gameRule.apply(this));
  }

  private void add(Entity entity) {
    world.add(entity);
  }

  @PostConstruct
  public void init() {
    this.addEntities();
    this.add(player);
  }

  private void addEntities() {
    add(entityFactory.createRandom());
    add(entityFactory.createRandomSlider());
    add(entityFactory.createRandomAccelerating());
    add(entityFactory.newPickaxe());
  }

  private List<Chunk> getActiveChunks() {
    Location chunkIndex = getPlayer().getMovement().getLocation().getChunkIndex();
    World world = getWorld();
    List<Chunk> activeChunks = Lists.newLinkedList();
    for (int y = -1; y <= 1; y++) {
      for (int x = -1; x <= 1; x++) {
        world.getChunk(chunkIndex.add(x, y)).ifPresent(activeChunks::add);
      }
    }
    return activeChunks;
  }

  public List<Entity> getActiveEntities() {
    return getActiveChunks().stream()
        .map(Chunk::getEntities)
        .flatMap(List::stream)
        .collect(Collectors.toList());
  }
}
