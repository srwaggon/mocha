package mocha.client.gfx;

import com.google.common.collect.Maps;

import org.springframework.stereotype.Component;

import java.util.Map;

import javax.inject.Inject;

import javafx.scene.Group;
import mocha.client.gfx.sprite.SpriteSheet;
import mocha.client.gfx.sprite.SpriteSheetFactory;
import mocha.client.gfx.view.ChunkView;
import mocha.game.PlayerService;
import mocha.game.world.Location;
import mocha.game.world.chunk.Chunk;
import mocha.game.world.chunk.ChunkService;

@Component
public class BackgroundLayer extends Group {

  private SpriteSheetFactory spriteSheetFactory;
  private TileSpriteSelector tileSpriteSelector;
  private Map<Location, ChunkView> chunkViews = Maps.newConcurrentMap();
  private ChunkService chunkService;
  private PlayerService playerService;

  @Inject
  public BackgroundLayer(
      SpriteSheetFactory spriteSheetFactory,
      TileSpriteSelector tileSpriteSelector,
      ChunkService chunkService,
      PlayerService playerService
  ) {
    this.spriteSheetFactory = spriteSheetFactory;
    this.tileSpriteSelector = tileSpriteSelector;
    this.chunkService = chunkService;
    this.playerService = playerService;
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
    Location location = getCenter();
    return chunkLocation.getX() < location.getX() - Chunk.getWidth() ||
        chunkLocation.getX() > location.getX() + Chunk.getWidth() ||
        chunkLocation.getY() < location.getY() - Chunk.getHeight() ||
        chunkLocation.getY() > location.getY() + Chunk.getHeight();
  }

  private void drawChunks() {
    Location location = getCenter();
    for (int y = 0; y < 1; y++) {
      for (int x = 0; x < 1; x++) {
        Location chunkLocation = new Location(location.getX() + x * Chunk.getWidth(), location.getY() + y * Chunk.getHeight());
        Chunk chunk = chunkService.getChunkAt(chunkLocation);
        drawChunk(chunkLocation, chunk);
      }
    }
  }

  private Location getCenter() {
    return playerService.getEntityForPlayer().getLocation();
  }

  private void drawChunk(Location chunkLocation, Chunk chunk) {
    ChunkView chunkView = getChunkView(chunkLocation, chunk);
    chunkView.setChunk(chunk);
    chunkView.render();
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
    ChunkView chunkView = new ChunkView(chunk, spriteSheet, dirtTiles, grassTiles, waterTiles, stoneTiles, tileSpriteSelector, chunkService);
    chunkViews.put(chunkLocation, chunkView);
    getChildren().add(chunkView);
    return chunkView;
  }

}
