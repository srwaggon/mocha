package mocha.client.gfx;

import org.springframework.stereotype.Component;

import javax.inject.Inject;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import mocha.client.gfx.sprite.ScaleProvider;
import mocha.client.gfx.sprite.Sprite;
import mocha.client.gfx.sprite.SpriteSheet;
import mocha.client.gfx.sprite.SpriteSheetFactory;
import mocha.client.gfx.view.Camera;
import mocha.game.world.Location;
import mocha.game.world.chunk.Chunk;
import mocha.game.world.chunk.ChunkService;
import mocha.game.world.chunk.tile.TileType;

@Component
public class BackgroundLayer {

  private final SpriteSheet dirtTiles;
  private ChunkService chunkService;
  private final SpriteSheet grassTiles;
  private final SpriteSheet waterTiles;
  private final SpriteSheet stoneTiles;
  private TileSpriteSelector tileSpriteSelector;
  private Camera camera;
  private ScaleProvider scaleProvider;

  @Inject
  public BackgroundLayer(
      SpriteSheetFactory spriteSheetFactory,
      TileSpriteSelector tileSpriteSelector,
      ChunkService chunkService,
      Camera camera,
      ScaleProvider scaleProvider
  ) {
    this.tileSpriteSelector = tileSpriteSelector;
    this.chunkService = chunkService;
    this.dirtTiles = spriteSheetFactory.newDirtTiles();
    this.grassTiles = spriteSheetFactory.newGrassTiles();
    this.waterTiles = spriteSheetFactory.newWaterTiles();
    this.stoneTiles = spriteSheetFactory.newStoneTiles();
    this.camera = camera;
    this.scaleProvider = scaleProvider;
  }

  public void render(long now, GraphicsContext graphics) {
    Rectangle2D bounds = camera.getBounds();
    int chunkHeight = Chunk.getHeight();
    int chunkWidth = Chunk.getWidth();
    for (double y = bounds.getMinY(); y < bounds.getMaxY() + chunkHeight; y += chunkHeight) {
      for (double x = bounds.getMinX(); x < bounds.getMaxX() + chunkWidth; x += chunkWidth) {
        drawChunk(graphics, new Location(x, y));
      }
    }
  }


  private void drawChunk(GraphicsContext graphics, Location location) {
    drawTiles(graphics, chunkService.getOrCreateChunkAt(location));
  }

  private void drawTiles(GraphicsContext graphics, Chunk chunk) {
    Location origin = chunk.getLocation();
    for (int y = 0; y < TileType.SIZE; y++) {
      for (int x = 0; x < TileType.SIZE; x++) {
        int tileX = origin.getX() + x * TileType.SIZE;
        int tileY = origin.getY() + y * TileType.SIZE;
        Location tileLocation = new Location(tileX, tileY);
        Sprite sprite = getSprite(tileLocation);

        double spriteX = getScale() * (tileX - camera.getBounds().getMinX());
        double spriteY = getScale() * (tileY - camera.getBounds().getMinY());
        sprite.render(graphics, spriteX, spriteY);
      }
    }
  }

  private Sprite getSprite(Location tileLocation) {
    TileType tileType = chunkService.getTileAt(tileLocation);
    SpriteSheet spriteSheet = selectSpriteSheet(tileType);
    int spriteIndex = tileSpriteSelector.selectSprite(tileLocation);
    return spriteSheet.getSprite(spriteIndex, getScale() * 2.0);
  }

  private SpriteSheet selectSpriteSheet(TileType tileType) {
    switch (tileType) {
      case GRASS:
        return grassTiles;
      case WATER:
        return waterTiles;
      case STONE:
        return stoneTiles;
      case DIRT:
      default:
        return dirtTiles;
    }
  }

  private double getScale() {
    return scaleProvider.getScale();
  }

}
