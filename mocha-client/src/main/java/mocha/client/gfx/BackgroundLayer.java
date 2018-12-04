package mocha.client.gfx;

import org.springframework.stereotype.Component;

import javax.inject.Inject;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import mocha.client.gfx.sprite.SpriteSheet;
import mocha.client.gfx.sprite.SpriteSheetFactory;
import mocha.client.gfx.view.Camera;
import mocha.game.world.Location;
import mocha.game.world.chunk.Chunk;
import mocha.game.world.chunk.ChunkService;
import mocha.game.world.tile.TileType;

@Component
public class BackgroundLayer {

  private final SpriteSheet spriteSheet;
  private final SpriteSheet dirtTiles;
  private ChunkService chunkService;
  private final SpriteSheet grassTiles;
  private final SpriteSheet waterTiles;
  private final SpriteSheet stoneTiles;
  private TileSpriteSelector tileSpriteSelector;
  private Camera camera;

  @Inject
  public BackgroundLayer(
      SpriteSheetFactory spriteSheetFactory,
      TileSpriteSelector tileSpriteSelector,
      ChunkService chunkService,
      Camera camera
  ) {
    this.tileSpriteSelector = tileSpriteSelector;
    this.chunkService = chunkService;
    this.spriteSheet = spriteSheetFactory.newSpriteSheet();
    this.dirtTiles = spriteSheetFactory.newDirtTiles();
    this.grassTiles = spriteSheetFactory.newGrassTiles();
    this.waterTiles = spriteSheetFactory.newWaterTiles();
    this.stoneTiles = spriteSheetFactory.newStoneTiles();
    this.camera = camera;
  }

  public void render(long now, GraphicsContext graphics) {
    drawChunk(graphics, camera.topLeft());
    drawChunk(graphics, camera.topRight());
    drawChunk(graphics, camera.bottomLeft());
    drawChunk(graphics, camera.bottomRight());
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
        Image sprite = getSprite(tileLocation);
        double spriteX = tileX - camera.getBounds().getMinX();
        double spriteY = tileY - camera.getBounds().getMinY();
        graphics.drawImage(sprite, spriteX, spriteY);
      }
    }
  }

  private Image getSprite(Location tileLocation) {
    TileType tileType = chunkService.getTileAt(tileLocation);
    SpriteSheet spriteSheet = selectSpriteSheet(tileType);
    int spriteIndex = tileSpriteSelector.selectSprite(tileLocation);
    return spriteSheet.getSprite(spriteIndex, getScale());
  }

  private SpriteSheet selectSpriteSheet(TileType tileType) {
    switch (tileType) {
      case DIRT:
        return dirtTiles;
      case GRASS:
        return grassTiles;
      case WATER:
        return waterTiles;
      case STONE:
        return stoneTiles;
      default:
        return spriteSheet;
    }
  }

  private double getScale() {
    return 2.0;
  }

}
