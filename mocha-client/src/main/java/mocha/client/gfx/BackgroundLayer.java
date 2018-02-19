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
    SpriteSheet spriteSheet = spriteSheetFactory.newSpriteSheet();
    SpriteSheet dirtTiles = spriteSheetFactory.newDirtTiles();
    SpriteSheet grassTiles = spriteSheetFactory.newGrassTiles();
    SpriteSheet waterTiles = spriteSheetFactory.newWaterTiles();
    SpriteSheet stoneTiles = spriteSheetFactory.newStoneTiles();
    ChunkView chunkView = new ChunkView(chunk, game, spriteSheet, dirtTiles, grassTiles, waterTiles, stoneTiles, tileSpriteSelector);
    chunkViews.put(chunkIndex, chunkView);
    getChildren().add(chunkView);
    return chunkView;
  }

}
