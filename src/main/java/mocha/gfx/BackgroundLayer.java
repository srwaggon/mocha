package mocha.gfx;

import com.google.common.collect.Maps;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;

import javax.inject.Inject;

import javafx.scene.Group;
import mocha.game.Game;
import mocha.game.world.Location;
import mocha.game.world.chunk.Chunk;
import mocha.gfx.sprite.SpriteSheet;
import mocha.gfx.sprite.SpriteSheetFactory;
import mocha.gfx.view.ChunkView;

@Component
public class BackgroundLayer extends Group {

  private final Game game;
  private final SpriteSheet spriteSheet;
  private TileSpriteSelector tileSpriteSelector;
  private Map<Location, ChunkView> chunkViews = Maps.newConcurrentMap();

  @Inject
  public BackgroundLayer(Game game, SpriteSheetFactory spriteSheetFactory, TileSpriteSelector tileSpriteSelector) {
    this.game = game;
    spriteSheet = spriteSheetFactory.newSpriteSheet();
    this.tileSpriteSelector = tileSpriteSelector;
  }

  public void render() {
    purgeNonVisibleChunkViews();
    drawChunks();
  }

  private void purgeNonVisibleChunkViews() {
    chunkViews.keySet().stream().filter(this::isChunkOffScreen)
        .forEach(chunkIndex -> {
          getChildren().remove(chunkViews.get(chunkIndex));
          chunkViews.remove(chunkIndex);
        });
  }

  private boolean isChunkOffScreen(Location chunkIndex) {
    Location playerChunkIndex = game.getPlayer().getMovement().getLocation().getChunkIndex();
    return chunkIndex.getX() < playerChunkIndex.getX() - 1 ||
        chunkIndex.getX() > playerChunkIndex.getX() + 1 ||
        chunkIndex.getY() < playerChunkIndex.getY() - 1 ||
        chunkIndex.getY() > playerChunkIndex.getY() + 1;
  }

  private void drawChunks() {
    Location playerChunkIndex = game.getPlayer().getMovement().getLocation().getChunkIndex();
    for (int y = -1; y <= 1; y++) {
      for (int x = -1; x <= 1; x++) {
        Location chunkIndex = new Location(playerChunkIndex.getX() + x, playerChunkIndex.getY() + y);
        Optional<Chunk> chunkOptional = game.getWorld().getChunk(chunkIndex);
        if (chunkOptional.isPresent()) {
          getChunkView(chunkIndex, chunkOptional.get()).render(x, y);
        }
      }
    }
  }

  private ChunkView getChunkView(Location chunkIndex, Chunk chunk) {
    if (chunkViews.containsKey(chunkIndex)) {
      return chunkViews.get(chunkIndex);
    }
    ChunkView chunkView = new ChunkView(chunk, game, spriteSheet, tileSpriteSelector);
    chunkViews.put(chunkIndex, chunkView);
    getChildren().add(chunkView);
    return chunkView;
  }

}
