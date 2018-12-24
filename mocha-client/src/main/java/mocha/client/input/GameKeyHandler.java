package mocha.client.input;

import com.google.common.eventbus.Subscribe;

import org.springframework.stereotype.Component;

import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import mocha.client.event.ClientEventBus;
import mocha.client.gfx.sprite.Sprite;
import mocha.client.gfx.sprite.SpriteService;
import mocha.client.input.event.GameKeyEvent;
import mocha.game.GameLogic;
import mocha.game.player.Player;
import mocha.game.world.Direction;
import mocha.game.world.entity.Entity;
import mocha.game.world.entity.movement.command.EntityMoveCommand;
import mocha.game.world.item.ItemPrototype;
import mocha.game.world.item.ItemPrototypeService;
import mocha.game.world.item.ItemPrototypeUpdatePacket;
import mocha.net.packet.PacketFactory;
import mocha.shared.Repository;

@Component
public class GameKeyHandler {

  private Repository<Player, Integer> playerRepository;
  private Repository<Entity, Integer> entityRepository;
  private ClientEventBus eventBus;
  private PacketFactory packetFactory;
  private GameLogic gameLogic;
  private SpriteService spriteService;
  private ItemPrototypeService itemPrototypeService;

  @Inject
  public GameKeyHandler(
      Repository<Player, Integer> playerRepository,
      Repository<Entity, Integer> entityRepository,
      ClientEventBus eventBus,
      PacketFactory packetFactory,
      GameLogic gameLogic,
      SpriteService spriteService,
      ItemPrototypeService itemPrototypeService
  ) {
    this.playerRepository = playerRepository;
    this.entityRepository = entityRepository;
    this.eventBus = eventBus;
    this.packetFactory = packetFactory;
    this.gameLogic = gameLogic;
    this.spriteService = spriteService;
    this.itemPrototypeService = itemPrototypeService;
  }

  @PostConstruct
  public void init() {
    eventBus.register(this);
  }

  @Subscribe
  public void handle(GameKeyEvent gameKeyEvent) {
    handleIfStartMove(gameKeyEvent);
    handleIfStopMove(gameKeyEvent);
    handleIfPickup(gameKeyEvent);
    handleIfNextSprite(gameKeyEvent);
  }

  private void handleIfNextSprite(GameKeyEvent gameKeyEvent) {
    if (gameKeyEvent.getGameKey().equals(GameKey.MENU) && gameKeyEvent.isDown()) {
//      findPlayerEntity().ifPresent(this::cycleEntitySprite);
      ItemPrototype pickaxePrototype = itemPrototypeService.findById(1);
      pickaxePrototype.setSpriteId(getNextSpriteId(pickaxePrototype.getSpriteId()));
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

  private void handleIfStartMove(GameKeyEvent gameKeyEvent) {
    if (!gameKeyEvent.isDown()) {
      return;
    }
    getDirection(gameKeyEvent)
        .ifPresent(direction ->
            findPlayerEntity().ifPresent(entity ->
                gameLogic.handle(buildEntityStartMoveCommand(entity, direction))));
  }

  private void handleIfStopMove(GameKeyEvent gameKeyEvent) {
    if (gameKeyEvent.isDown()) {
      return;
    }
    getDirection(gameKeyEvent)
        .ifPresent(direction ->
            findPlayerEntity().ifPresent(entity ->
                gameLogic.handle(buildEntityStopMoveCommand(entity, direction))));
  }

  private void handleIfPickup(GameKeyEvent gameKeyEvent) {
    if (!gameKeyEvent.getGameKey().equals(GameKey.ACTION2)) {
      return;
    }
    findPlayerEntity().ifPresent(entity ->
        eventBus.postSendPacketEvent(packetFactory.newPickUpItemPacket(entity)));
  }

  private Optional<Entity> findPlayerEntity() {
    return entityRepository.findById(getPlayerEntityId());
  }

  private Integer getPlayerEntityId() {
    return getPlayer().getEntity().getId();
  }

  private Player getPlayer() {
    return playerRepository.findAll().get(0);
  }

  private EntityMoveCommand buildEntityStartMoveCommand(Entity entity, Direction direction) {
    return buildEntityMoveCommand(entity, direction, true);
  }

  private EntityMoveCommand buildEntityStopMoveCommand(Entity entity, Direction direction) {
    return buildEntityMoveCommand(entity, direction, false);
  }

  private EntityMoveCommand buildEntityMoveCommand(Entity entity, Direction direction, boolean isStart) {
    return EntityMoveCommand.builder()
        .entityId(entity.getId())
        .location(entity.getLocation())
        .direction(direction)
        .xOffset(isStart ? direction.getXMultiplier() : 0)
        .yOffset(isStart ? direction.getYMultiplier() : 0)
        .build();
  }

  private Optional<Direction> getDirection(GameKeyEvent gameKeyEvent) {
    switch (gameKeyEvent.getGameKey()) {
      case UP:
        return Optional.of(Direction.NORTH);
      case RIGHT:
        return Optional.of(Direction.EAST);
      case DOWN:
        return Optional.of(Direction.SOUTH);
      case LEFT:
        return Optional.of(Direction.WEST);
      default:
        return Optional.empty();
    }
  }
}
