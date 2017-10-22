package mocha.game;

import org.springframework.stereotype.Component;

import javax.inject.Inject;

import mocha.game.world.chunk.Chunk;
import mocha.game.world.chunk.ChunkView;
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
    this.chunkView.setChunk(getPlayersCurrentMap());
    chunkView.draw(mochaCanvas, 0, 0);
  }

  private Chunk getPlayersCurrentMap() {
    int playerChunkId = game.getPlayer().getChunkId();
    return game.getWorld().getMapById(playerChunkId);
  }
}
