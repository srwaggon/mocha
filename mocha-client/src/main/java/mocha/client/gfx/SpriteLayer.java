package mocha.client.gfx;

import com.google.common.collect.Maps;
import com.google.common.eventbus.Subscribe;

import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.Map;

import javax.inject.Inject;

import javafx.application.Platform;
import javafx.scene.Group;
import mocha.client.gfx.sprite.SpriteSheet;
import mocha.client.gfx.sprite.SpriteSheetFactory;
import mocha.client.gfx.view.EntityView;
import mocha.client.gfx.view.PlayerView;
import mocha.game.Game;
import mocha.game.world.entity.AddEntityEvent;
import mocha.game.world.entity.Entity;
import mocha.game.world.entity.RemoveEntityEvent;

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
  public void handle(AddEntityEvent addEntityEvent) {
    Platform.runLater(() -> addEntityView(addEntityEvent.getEntity()));
  }

  @Subscribe
  public void handle(RemoveEntityEvent removeEntityEvent) {
    Platform.runLater(() -> removeEntityView(removeEntityEvent.getEntity()));
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
