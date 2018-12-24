package mocha.client.gfx;

import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.Optional;

import javax.inject.Inject;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import mocha.client.gfx.sprite.Sprite;
import mocha.client.gfx.sprite.SpriteService;
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

  private Repository<Movement, Integer> movementRepository;
  private ChunkService chunkService;
  private Camera camera;
  private EntitiesInChunkService entitiesInChunkService;
  private SpriteService spriteService;

  @Inject
  public SpriteLayer(
      Repository<Movement, Integer> movementRepository,
      ChunkService chunkService, Camera camera,
      EntitiesInChunkService entitiesInChunkService,
      SpriteService spriteService
  ) {
    this.movementRepository = movementRepository;
    this.chunkService = chunkService;
    this.camera = camera;
    this.entitiesInChunkService = entitiesInChunkService;
    this.spriteService = spriteService;
  }

  public void render(long now, GraphicsContext graphics) {
    Rectangle2D bounds = camera.getBounds();
    for (double y = bounds.getMinY(); y < bounds.getMaxY(); y += Chunk.getHeight() - 1) {
      for (double x = bounds.getMinX(); x < bounds.getMaxX(); x += Chunk.getWidth() - 1) {
        renderEntitiesInChunkAt(now, graphics, new Location(x, y));
      }
    }
  }

  private void renderEntitiesInChunkAt(long now, GraphicsContext graphics, Location location) {
    Chunk chunk = chunkService.getOrCreateChunkAt(location);
    renderEntitiesInChunk(now, graphics, chunk);
  }

  private void renderEntitiesInChunk(long now, GraphicsContext graphics, Chunk chunk) {
    entitiesInChunkService.getEntitiesInChunk(chunk).stream()
        .sorted(Comparator.comparingInt(entity -> entity.getLocation().getY()))
        .forEach(entity -> renderEntity(now, graphics, entity));
  }

  private void renderEntity(long now, GraphicsContext graphics, Entity entity) {
    Sprite sprite = getFrame(now, entity);
    double spriteX = entity.getLocation().getX() - camera.getBounds().getMinX();
    double spriteY = entity.getLocation().getY() - camera.getBounds().getMinY();
    sprite.render(graphics, spriteX, spriteY);
  }

  private Sprite getFrame(long now, Entity entity) {
    return getSpriteId(now, entity);
  }

  private Sprite getSpriteId(long now, Entity entity) {
    String spriteId = entity.getSpriteId();

    Optional<Movement> movement = movementRepository.findById(entity.getId());
    if (!movement.isPresent() || !movement.get().isMoving()) {
      return spriteService.findById(spriteId);
    }

    double frameId = now / RenderLoop.FRAME_LIFESPAN;
    return frameId % 16 < 8 ? spriteService.findById(spriteId) : spriteService.getNext(spriteId);
  }
}
