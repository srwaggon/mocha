package mocha.client.gfx;

import com.google.common.collect.Maps;

import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.Map;

import javax.inject.Inject;

import javafx.scene.Group;
import mocha.client.gfx.sprite.SpriteSheet;
import mocha.client.gfx.sprite.SpriteSheetFactory;
import mocha.client.gfx.view.EntityView;
import mocha.game.world.entity.Entity;
import mocha.game.world.entity.movement.Movement;
import mocha.shared.Repository;

@Component
public class SpriteLayer extends Group {

  private Map<Entity, EntityView> entityViews = Maps.newIdentityHashMap();

  private SpriteSheet spriteSheet;
  private Repository<Entity, Integer> entityRepository;
  private Repository<Movement, Integer> movementRepository;


  @Inject
  public SpriteLayer(
      SpriteSheetFactory spriteSheetFactory,
      Repository<Entity, Integer> entityRepository,
      Repository<Movement, Integer> movementRepository) {
    spriteSheet = spriteSheetFactory.newSpriteSheet();
    this.entityRepository = entityRepository;
    this.movementRepository = movementRepository;
  }

  private void addEntityViews() {
    entityRepository.findAll().stream()
        .sorted(Comparator.comparingInt(entity -> entity.getLocation().getY()))
        .forEach(this::addEntityView);
  }

  private void addEntityView(Entity entity) {
    EntityView entityView = new EntityView(entity, movementRepository, spriteSheet);
    entityViews.put(entity, entityView);
    getChildren().add(entityView);
  }

  private void removeEntityViews() {
    entityViews.keySet().forEach(this::removeEntityView);
    entityViews.clear();
  }

  private void removeEntityView(Entity entity) {
    EntityView entityView = entityViews.get(entity);
    getChildren().remove(entityView);
  }

  public void render(long now) {
    this.removeEntityViews();
    addEntityViews();
    entityViews.values().forEach(entityView -> entityView.render(now));
  }
}
