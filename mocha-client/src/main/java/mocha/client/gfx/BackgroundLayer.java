package mocha.client.gfx;

import org.springframework.stereotype.Component;

import javax.inject.Inject;

import javafx.scene.Group;
import mocha.client.gfx.sprite.SpriteSheet;
import mocha.client.gfx.sprite.SpriteSheetFactory;
import mocha.client.gfx.view.ChunkView;
import mocha.game.PlayerService;
import mocha.game.world.chunk.Chunk;
import mocha.game.world.chunk.ChunkService;

@Component
public class BackgroundLayer extends Group {

  private ChunkView chunkView;
  private ChunkService chunkService;
  private PlayerService playerService;

  @Inject
  public BackgroundLayer(
      SpriteSheetFactory spriteSheetFactory,
      TileSpriteSelector tileSpriteSelector,
      ChunkService chunkService,
      PlayerService playerService
  ) {
    this.playerService = playerService;
    this.chunkService = chunkService;
    SpriteSheet spriteSheet = spriteSheetFactory.newSpriteSheet();
    SpriteSheet dirtTiles = spriteSheetFactory.newDirtTiles();
    SpriteSheet grassTiles = spriteSheetFactory.newGrassTiles();
    SpriteSheet waterTiles = spriteSheetFactory.newWaterTiles();
    SpriteSheet stoneTiles = spriteSheetFactory.newStoneTiles();
    this.chunkView = new ChunkView(spriteSheet, dirtTiles, grassTiles, waterTiles, stoneTiles, tileSpriteSelector, chunkService);
    getChildren().add(chunkView);
  }

  public void render() {
    chunkView.setChunk(getChunkForPlayer());
    chunkView.render();
  }

  private Chunk getChunkForPlayer() {
    return chunkService.getOrCreateChunkByIndex(playerService.getEntityForPlayer().getLocation().getChunkIndex());
  }


}
