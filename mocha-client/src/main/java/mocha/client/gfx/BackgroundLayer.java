package mocha.client.gfx;

import com.google.common.collect.Maps;

import org.springframework.stereotype.Component;

import java.util.Map;

import javax.inject.Inject;

import javafx.scene.Group;
import mocha.client.gfx.sprite.SpriteSheet;
import mocha.client.gfx.sprite.SpriteSheetFactory;
import mocha.client.gfx.view.ChunkView;
import mocha.game.Game;
import mocha.game.world.Location;
import mocha.game.world.chunk.Chunk;

@Component
public class BackgroundLayer extends Group {

  private final Game game;
  private SpriteSheetFactory spriteSheetFactory;
  private TileSpriteSelector tileSpriteSelector;
  private Map<Location, ChunkView> chunkViews = Maps.newConcurrentMap();

  @Inject
  public BackgroundLayer(Game game, SpriteSheetFactory spriteSheetFactory, TileSpriteSelector tileSpriteSelector) {
    this.game = game;
    this.spriteSheetFactory = spriteSheetFactory;
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

  private boolean isChunkOffScreen(Location chunkLocation) {
    Location location = Location.at(0,0);
    return chunkLocation.getX() < location.getX() - Chunk.getWidth() ||
        chunkLocation.getX() > location.getX() + Chunk.getWidth() ||
        chunkLocation.getY() < location.getY() - Chunk.getHeight() ||
        chunkLocation.getY() > location.getY() + Chunk.getHeight();
  }

  private void drawChunks() {
    Location location = Location.at(0,0);
    for (int y = 0; y < 1; y++) {
      for (int x = 0; x < 1; x++) {
        Location chunkLocation = new Location(location.getX() + x * Chunk.getWidth(), location.getY() + y * Chunk.getHeight());
        game.getChunkRepository().getChunkAt(chunkLocation).ifPresent((chunk) -> {
          ChunkView chunkView = getChunkView(chunkLocation, chunk);
          chunkView.setChunk(chunk);
          chunkView.render();
        });
      }
    }
  }

  private ChunkView getChunkView(Location chunkLocation, Chunk chunk) {
    if (chunkViews.containsKey(chunkLocation)) {
      return chunkViews.get(chunkLocation);
    }
    SpriteSheet spriteSheet = spriteSheetFactory.newSpriteSheet();
    SpriteSheet dirtTiles = spriteSheetFactory.newDirtTiles();
    SpriteSheet grassTiles = spriteSheetFactory.newGrassTiles();
    SpriteSheet waterTiles = spriteSheetFactory.newWaterTiles();
    SpriteSheet stoneTiles = spriteSheetFactory.newStoneTiles();
    ChunkView chunkView = new ChunkView(chunk, game, spriteSheet, dirtTiles, grassTiles, waterTiles, stoneTiles, tileSpriteSelector);
    chunkViews.put(chunkLocation, chunkView);
    getChildren().add(chunkView);
    return chunkView;
  }

}
