package mocha.client.gfx;

import com.google.common.collect.Maps;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;

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
    Location playerLocation = game.getPlayer().getMovement().getLocation();
    return chunkLocation.getX() < playerLocation.getX() - Chunk.getWidth() ||
        chunkLocation.getX() > playerLocation.getX() + Chunk.getWidth() ||
        chunkLocation.getY() < playerLocation.getY() - Chunk.getHeight() ||
        chunkLocation.getY() > playerLocation.getY() + Chunk.getHeight();
  }

  private void drawChunks() {
    Location playerChunk = game.getPlayer().getMovement().getLocation();
    for (int y = -1; y <= 1; y++) {
      for (int x = -1; x <= 1; x++) {
        Location chunkLocation = new Location(playerChunk.getX() + x * Chunk.getWidth(), playerChunk.getY() + y * Chunk.getHeight());
        int finalX = x * Chunk.getWidth();
        int finalY = y * Chunk.getHeight();
        game.getWorld().getChunkAt(chunkLocation).ifPresent((chunk) ->
            getChunkView(chunkLocation, chunk).render(finalX, finalY));
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
