package mocha.game;

import org.springframework.stereotype.Component;

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

  @Inject
  private ChunkView chunkView;

  @Override
  public void draw(MochaCanvas mochaCanvas, int x, int y) {
    Location playerLocation = game.getPlayer().getMovement().getLocation();
    Chunk playerChunk = game.getWorld().getChunkAt(playerLocation);

    chunkView.setChunk(playerChunk);
    chunkView.draw(mochaCanvas, 0, 0);

    int chunkXOffset = (int) (playerLocation.getX() / Chunk.getWidth()) * Chunk.getWidth();
    int chunkYOffset = (int) (playerLocation.getY() / Chunk.getHeight()) * Chunk.getHeight();
    game.getEntities().forEach(entity -> {
      int entityViewX = (int) (entity.getMovement().getLocation().getX() - chunkXOffset);
      int entityViewY = (int) (entity.getMovement().getLocation().getY() - chunkYOffset);
      new EntityView(entity).draw(mochaCanvas, entityViewX, entityViewY);
    });
  }
}
