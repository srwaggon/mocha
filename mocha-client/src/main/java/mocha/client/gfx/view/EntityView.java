package mocha.client.gfx.view;

import java.util.Optional;

import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import mocha.client.gfx.RenderLoop;
import mocha.client.gfx.sprite.SpriteSheet;
import mocha.game.world.entity.Entity;
import mocha.game.world.entity.movement.Movement;
import mocha.shared.Repository;

public class EntityView extends Canvas {

  private Entity entity;
  private Repository<Movement, Integer> movementRepository;
  private SpriteSheet spriteSheet;

  public EntityView(Entity entity, Repository<Movement, Integer> movementRepository, SpriteSheet spriteSheet) {
    this.entity = entity;
    this.movementRepository = movementRepository;
    this.spriteSheet = spriteSheet;
    this.setOnMouseClicked(event -> System.out.println(this.entity + " clicked!"));
  }

  public void render(long now) {
    this.setLayoutX(entity.getLocation().getX());
    this.setLayoutY(entity.getLocation().getY());
    this.setWidth(32);
    this.setHeight(32);
    Image sprite = getFrame(now);
    this.getGraphicsContext2D().drawImage(sprite, 0, 0);
  }

  private Image getFrame(long now) {
    int spriteId = getSpriteId(now);

    return spriteSheet.getSprite(spriteId, entity.getScale());
  }

  private int getSpriteId(long now) {
    int spriteId = entity.getSpriteId();

    Optional<Movement> movement = movementRepository.findById(entity.getId());
    if (!movement.isPresent() || !movement.get().isMoving()) {
      return spriteId;
    }

    double frameId = now / RenderLoop.FRAME_LIFESPAN;
    return frameId % 16 < 8 ? spriteId : spriteId + 1;
  }

}
