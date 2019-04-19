package mocha.client.input;

import org.springframework.stereotype.Component;

import javax.inject.Inject;

import mocha.client.event.ClientEventBus;
import mocha.client.gfx.sprite.Sprite;
import mocha.client.gfx.sprite.SpriteService;
import mocha.client.input.event.GameKeyEvent;
import mocha.game.player.PlayerService;
import mocha.game.world.entity.Entity;
import mocha.game.world.item.ItemPrototypeUpdatePacket;
import mocha.game.world.item.itemprototype.ItemPrototype;
import mocha.game.world.item.itemprototype.ItemPrototypeService;

@Component
class NextSpriteHandler implements GameKeyHandler {
  private PlayerService playerService;
  private ItemPrototypeService itemPrototypeService;
  private ClientEventBus eventBus;
  private SpriteService spriteService;

  @Inject
  NextSpriteHandler(
      PlayerService playerService,
      ItemPrototypeService itemPrototypeService,
      ClientEventBus eventBus,
      SpriteService spriteService
  ) {
    this.playerService = playerService;
    this.itemPrototypeService = itemPrototypeService;
    this.eventBus = eventBus;
    this.spriteService = spriteService;
  }

  @Override
  public void handle(GameKeyEvent gameKeyEvent) {
    if (gameKeyEvent.getGameKey().equals(GameKey.MENU) && gameKeyEvent.isDown()) {
      playerService.findPlayerEntity().ifPresent(this::cycleEntitySprite);
      playerService.findPlayerEntity().ifPresent(this::increaseScale);
      ItemPrototype pickaxePrototype = itemPrototypeService.findById(1);
      pickaxePrototype.setSpriteId(this.getNextSpriteId(pickaxePrototype.getSpriteId()));
      eventBus.postSendPacketEvent(new ItemPrototypeUpdatePacket(pickaxePrototype));
    }
  }

  private void cycleEntitySprite(Entity entity) {
    String currentSpriteId = entity.getSpriteId();
    String nextSpriteId = getNextSpriteId(currentSpriteId);
    entity.setSpriteId(nextSpriteId);
  }

  private String getNextSpriteId(String currentSpriteId) {
    Sprite currentSprite = spriteService.findById(currentSpriteId);
    Sprite nextSprite = spriteService.getNext(currentSprite);
    return nextSprite.getId();
  }

  private void increaseScale(Entity entity) {
    entity.setScale(entity.getScale() + .1);
  }
}
