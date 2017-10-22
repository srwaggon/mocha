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
import mocha.game.rule.TransitionBetweenMapsRule;
import mocha.game.world.World;
import mocha.game.world.entity.Entity;
import mocha.game.world.entity.EntityFactory;
import mocha.game.world.entity.brain.InputBrain;
import mocha.game.world.map.MapFactory;

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

  private List<GameRule> gameRules = Lists.newArrayList();

  public Game(World world, EntityFactory entityFactory) {
    this.world = world;
    this.entityFactory = entityFactory;
  }

  @PostConstruct
  void init() {
    addMaps();
    addEntities();
    addRules();
  }

  private void addEntities() {
    addPlayer();
    addNpcs();
  }

  private void addRules() {
    gameRules.add(new BrainRule());
    gameRules.add(new MovementRule());
    gameRules.add(new TransitionBetweenMapsRule());
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
    gameRules.forEach(gameRule -> gameRule.apply(this));
  }
}
