package mocha.gfx;

import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import javafx.scene.Group;
import mocha.game.Game;
import mocha.gfx.sprite.SpriteSheet;
import mocha.gfx.sprite.SpriteSheetFactory;
import mocha.gfx.view.EntityView;
import mocha.gfx.view.PlayerView;

@Component
public class SpriteLayer extends Group {

  private List<EntityView> entityViews;

  private PlayerView playerView;

  @Inject
  public SpriteLayer(Game game, SpriteSheetFactory spriteSheetFactory) {
    SpriteSheet spriteSheet = spriteSheetFactory.newSpriteSheet();

    addTileItemViews();
    addEntityViews(game, spriteSheet);
    addPlayerView(game, spriteSheet);
  }

  private void addTileItemViews() {

  }

  private void addEntityViews(Game game, SpriteSheet spriteSheet) {
    this.entityViews = game.getEntities().stream()
        .filter(entity -> !entity.equals(game.getPlayer()))
        .sorted(Comparator.comparingInt(entity -> entity.getMovement().getLocation().getYAsInt()))
        .map(entity -> new EntityView(entity, game.getPlayer(), spriteSheet, 2.0))
        .collect(Collectors.toList());
    getChildren().addAll(entityViews);
  }

  private void addPlayerView(Game game, SpriteSheet spriteSheet) {
    playerView = new PlayerView(game.getPlayer(), spriteSheet);
    getChildren().add(playerView);
  }

  public void render() {
    entityViews.forEach(EntityView::render);
    playerView.render();
  }
}
