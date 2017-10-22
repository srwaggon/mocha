package mocha.game;

import com.google.common.collect.Lists;

import org.springframework.stereotype.Component;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import lombok.Data;
import lombok.NoArgsConstructor;
import mocha.game.rule.BrainRule;
import mocha.game.rule.GameRule;
import mocha.game.rule.MovementRule;
import mocha.game.world.World;
import mocha.game.world.chunk.Chunk;
import mocha.game.world.chunk.ChunkFactory;
import mocha.game.world.entity.Entity;
import mocha.game.world.entity.EntityFactory;
import mocha.game.world.entity.brain.InputBrain;

@Data
@Component
@NoArgsConstructor
public class Game implements Tickable {

  @Inject
  private EntityFactory entityFactory;
  @Inject
  private ChunkFactory chunkFactory;

  private World world;
  private Entity player;
  private List<Entity> entities = Lists.newArrayList();
  private List<GameRule> gameRules = Lists.newArrayList();

  @PostConstruct
  void init() {
    addWorld();
    addEntities();
    addRules();
  }

  private void addWorld() {
    world = new World(createChunks());
  }

  private Chunk[][] createChunks() {
    Chunk[][] chunks = new Chunk[16][16];
    for (int y = 0; y < 16; y++) {
      for (int x = 0; x < 16; x++) {
        chunks[y][x] = chunkFactory.newRandomDefault();
      }
    }
    return chunks;
  }

  private void addEntities() {
    addPlayer();
    addNpcs();
  }

  private void addRules() {
    gameRules.add(new BrainRule());
    gameRules.add(new MovementRule());
  }

  private void addNpcs() {
    for (int i = 0; i < 10; i++) {
      addEntity(entityFactory.createRandom());
      addEntity(entityFactory.createRandomSlider());
      addEntity(entityFactory.createRandomAccelerating());
    }
  }

  private void addPlayer() {
    this.player = entityFactory.createRandom();
    this.player.setBrain(new InputBrain(this.player));
    addEntity(this.player);
  }

  private void addEntity(Entity entity) {
    entities.add(entity);
  }

  public World getWorld() {
    return world;
  }

  public void tick(long now) {
    gameRules.forEach(gameRule -> gameRule.apply(this));
  }
}
