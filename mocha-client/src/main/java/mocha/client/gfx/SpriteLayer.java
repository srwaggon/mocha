package mocha.client.gfx;

import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.Optional;

import javax.inject.Inject;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import mocha.client.gfx.sprite.SpriteSheet;
import mocha.client.gfx.sprite.SpriteSheetFactory;
import mocha.client.gfx.view.Camera;
import mocha.game.world.Location;
import mocha.game.world.chunk.Chunk;
import mocha.game.world.chunk.ChunkService;
import mocha.game.world.entity.EntitiesInChunkService;
import mocha.game.world.entity.Entity;
import mocha.game.world.entity.movement.Movement;
import mocha.shared.Repository;

@Component
public class SpriteLayer {

  private SpriteSheet spriteSheet;
  private Repository<Movement, Integer> movementRepository;
  private ChunkService chunkService;
  private Camera camera;
  private EntitiesInChunkService entitiesInChunkService;

  @Inject
  public SpriteLayer(
      SpriteSheetFactory spriteSheetFactory,
      Repository<Movement, Integer> movementRepository,
      ChunkService chunkService, Camera camera,
      EntitiesInChunkService entitiesInChunkService
  ) {
    spriteSheet = spriteSheetFactory.newSpriteSheet();
    this.movementRepository = movementRepository;
    this.chunkService = chunkService;
    this.camera = camera;
    this.entitiesInChunkService = entitiesInChunkService;
  }

  public void render(long now, GraphicsContext graphics) {
    renderEntitiesInChunkAt(now, graphics, camera.topLeft());
    renderEntitiesInChunkAt(now, graphics, camera.topRight());
    renderEntitiesInChunkAt(now, graphics, camera.bottomLeft());
    renderEntitiesInChunkAt(now, graphics, camera.bottomRight());
  }

  private void renderEntitiesInChunkAt(long now, GraphicsContext graphics, Location location) {
    Chunk chunk = chunkService.getOrCreateChunkAt(location);
    entitiesInChunkService.getEntitiesInChunk(chunk).stream()
        .sorted(Comparator.comparingInt(entity -> entity.getLocation().getY()))
        .forEach(entity -> renderEntity(now, graphics, entity));
  }

  private void renderEntity(long now, GraphicsContext graphics, Entity entity) {
    Image sprite = getFrame(now, entity);
    double spriteX = entity.getLocation().getX() - camera.getBounds().getMinX();
    double spriteY = entity.getLocation().getY() - camera.getBounds().getMinY();
    graphics.drawImage(sprite, spriteX, spriteY);
  }

  private Image getFrame(long now, Entity entity) {
    int spriteId = getSpriteId(now, entity);
    return spriteSheet.getSprite(spriteId, entity.getScale());
  }

  private int getSpriteId(long now, Entity entity) {
    int spriteId = entity.getSpriteId();

    Optional<Movement> movement = movementRepository.findById(entity.getId());
    if (!movement.isPresent() || !movement.get().isMoving()) {
      return spriteId;
    }

    double frameId = now / RenderLoop.FRAME_LIFESPAN;
    return frameId % 16 < 8 ? spriteId : spriteId + 1;
  }
}
