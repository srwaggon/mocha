package mocha.gfx;

import com.google.common.collect.Maps;
import com.google.common.eventbus.Subscribe;

import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.Map;

import javax.inject.Inject;

import javafx.scene.Group;
import mocha.game.Game;
import mocha.game.world.entity.Entity;
import mocha.game.world.entity.RemoveEntityEvent;
import mocha.gfx.sprite.SpriteSheet;
import mocha.gfx.sprite.SpriteSheetFactory;
import mocha.gfx.view.EntityView;
import mocha.gfx.view.PlayerView;

@Component
public class SpriteLayer extends Group {

  private final Game game;
  private SpriteSheet spriteSheet;
  private Map<Entity, EntityView> entityViews = Maps.newIdentityHashMap();
  private PlayerView playerView;

  @Inject
  public SpriteLayer(Game game, SpriteSheetFactory spriteSheetFactory) {
    this.game = game;
    spriteSheet = spriteSheetFactory.newSpriteSheet();

    addEntityViews();
    addPlayerView();
  }

  private void addEntityViews() {
    game.getActiveEntities().stream()
        .filter(entity -> !entity.equals(game.getPlayer()))
        .sorted(Comparator.comparingInt(entity -> entity.getMovement().getLocation().getYAsInt()))
        .forEach(this::addEntityView);
  }

  private void addEntityView(Entity entity) {
    EntityView entityView = new EntityView(entity, game.getPlayer(), spriteSheet);
    entityViews.put(entity, entityView);
    getChildren().add(entityView);
  }

  @Subscribe
  public void handleRemoveEntityEvent(RemoveEntityEvent removeEntityEvent) {
    removeEntityView(removeEntityEvent.getEntity());
  }

  private void removeEntityView(Entity entity) {
    EntityView entityView = entityViews.remove(entity);
    getChildren().remove(entityView);
  }

  private void addPlayerView() {
    playerView = new PlayerView(game.getPlayer(), spriteSheet);
    getChildren().add(playerView);
  }

  public void render() {
    entityViews.values().forEach(EntityView::render);
    playerView.render();
  }
}
