package mocha.game;

import org.springframework.stereotype.Component;

import java.util.Optional;

import javax.inject.Inject;

import mocha.game.world.Location;
import mocha.game.world.chunk.Chunk;
import mocha.game.world.chunk.ChunkView;
import mocha.game.world.entity.EntityView;
import mocha.gfx.Drawable;
import mocha.gfx.MochaCanvas;

@Component
public class GameView implements Drawable {

  @Inject
  private Game game;

  @Override
  public void draw(MochaCanvas mochaCanvas, int xOffset, int yOffset) {
    drawChunks(mochaCanvas);

    drawEntities(mochaCanvas);

    drawPlayer(mochaCanvas);
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
        drawChunk(mochaCanvas, x, y, chunkOptional.get());
      }
    }
  }

  private void drawChunk(MochaCanvas mochaCanvas, int x, int y, Chunk chunk) {
    ChunkView chunkView = new ChunkView(chunk);
    int chunkXOffset = getChunkXOffset(mochaCanvas, x);
    int chunkYOffset = getChunkYOffset(mochaCanvas, y);
    chunkView.draw(mochaCanvas, chunkXOffset, chunkYOffset);
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
    game.getEntities().forEach(entity -> {
      Location location = entity.getMovement().getLocation();
      Location playerLocation = game.getPlayer().getMovement().getLocation();
      int xOffset = location.getXAsInt() - playerLocation.getXAsInt();
      int yOffset = location.getYAsInt() - playerLocation.getYAsInt();
      new EntityView(entity).draw(mochaCanvas, xOffset, yOffset);
    });
  }

  private void drawPlayer(MochaCanvas mochaCanvas) {
    int canvasXOffset = getCanvasXOffset(mochaCanvas);
    int canvasYOffset = getCanvasYOffset(mochaCanvas);
    EntityView entityView = new EntityView(game.getPlayer());
    entityView.draw(mochaCanvas, canvasXOffset, canvasYOffset);
  }
}
