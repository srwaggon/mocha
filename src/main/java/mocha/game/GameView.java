package mocha.game;

import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.Optional;
import java.util.function.IntConsumer;
import java.util.stream.IntStream;

import javax.inject.Inject;

import mocha.game.world.Location;
import mocha.game.world.chunk.Chunk;
import mocha.game.world.entity.Entity;
import mocha.game.world.entity.EntityView;
import mocha.game.world.tile.item.TileItem;
import mocha.gfx.Drawable;
import mocha.gfx.MochaCanvas;
import mocha.gfx.TileSpriteSelector;

@Component
public class GameView implements Drawable {

  @Inject
  private Game game;

  @Inject
  private TileSpriteSelector tileSpriteSelector;

  @Override
  public void draw(MochaCanvas mochaCanvas, int xOffset, int yOffset) {
    drawChunks(mochaCanvas);

    drawEntities(mochaCanvas);
  }

  private void drawChunks(MochaCanvas mochaCanvas) {
    Location playerChunkIndex = game.getPlayer().getMovement().getLocation().getChunkIndex();
    for (int y = -1; y <= 1; y++) {
      for (int x = -1; x <= 1; x++) {
        Location chunkIndex = new Location(playerChunkIndex.getX() + x, playerChunkIndex.getY() + y);
        Optional<Chunk> chunkOptional = game.getWorld().getChunk(chunkIndex);
        if (!chunkOptional.isPresent()) {
          continue;
        }
        drawChunk(mochaCanvas, chunkOptional.get(), x, y);
      }
    }
  }

  private void drawChunk(MochaCanvas mochaCanvas, Chunk chunkIndex, int x, int y) {
    int chunkXOffset = getChunkXOffset(mochaCanvas, x);
    int chunkYOffset = getChunkYOffset(mochaCanvas, y);
    drawTiles(mochaCanvas, chunkIndex, chunkXOffset, chunkYOffset);
  }

  private int getChunkXOffset(MochaCanvas mochaCanvas, int x) {
    int playerX = game.getPlayer().getMovement().getLocation().getXAsInt() % Chunk.getWidth();
    int playerOffsetX = -1 * ((Chunk.getWidth() + playerX) % Chunk.getWidth());
    int xOffset = x * Chunk.getWidth() + getCanvasXOffset(mochaCanvas);
    return playerOffsetX + xOffset;
  }

  private int getChunkYOffset(MochaCanvas mochaCanvas, int y) {
    int playerY = game.getPlayer().getMovement().getLocation().getYAsInt() % Chunk.getHeight();
    int playerOffsetY = -1 * ((Chunk.getHeight() + playerY) % Chunk.getHeight());
    int yOffset = y * Chunk.getHeight() + getCanvasYOffset(mochaCanvas);
    return playerOffsetY + yOffset;
  }

  private int getCanvasXOffset(MochaCanvas mochaCanvas) {
    return (int) (mochaCanvas.getWidth() / 2);
  }

  private int getCanvasYOffset(MochaCanvas mochaCanvas) {
    return (int) (mochaCanvas.getHeight() / 2);
  }

  private void drawEntities(MochaCanvas mochaCanvas) {
    Entity player = game.getPlayer();
    Location playerLocation = player.getMovement().getLocation();
    int canvasXOffset = getCanvasXOffset(mochaCanvas);
    int canvasYOffset = getCanvasYOffset(mochaCanvas);
    game.getEntities().stream()
        .sorted(Comparator.comparingInt(entity -> entity.getMovement().getLocation().getYAsInt()))
        .forEach(entity -> {
          if (entity.equals(player)) {
            new EntityView(game.getPlayer()).draw(mochaCanvas, canvasXOffset, canvasYOffset);
          }
          Location location = entity.getMovement().getLocation();
          int xOffset = location.getXAsInt() - playerLocation.getXAsInt() + canvasXOffset;
          int yOffset = location.getYAsInt() - playerLocation.getYAsInt() + canvasYOffset;
          new EntityView(entity).draw(mochaCanvas, xOffset, yOffset);
        });
  }

  private void drawTiles(MochaCanvas mochaCanvas, Chunk chunk, int xOffset, int yOffset) {
    IntConsumer drawRow = (yIndex) -> IntStream.range(0, Chunk.SIZE).forEach((xIndex) -> drawTile(mochaCanvas, chunk, xIndex, yIndex, xOffset, yOffset));
    IntStream.range(0, Chunk.SIZE).forEach(drawRow);
  }

  private void drawTile(MochaCanvas mochaCanvas, Chunk chunk, int xIndex, int yIndex, int xOffset, int yOffset) {
    int spriteId = tileSpriteSelector.selectSprite(chunk, xIndex, yIndex);
    double scale = 2.0;
    int spriteX = (int) (xIndex * 16 * scale) + xOffset;
    int spriteY = (int) (yIndex * 16 * scale) + yOffset;
    mochaCanvas.drawSprite(spriteId, spriteX, spriteY, scale);

    TileItem tileItem = chunk.getTile(xIndex, yIndex).getTileItem();
    if (tileItem != null) {
      mochaCanvas.drawSprite(tileItem.getSpriteId(), spriteX, spriteY, scale);
    }
  }

}
