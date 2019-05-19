package mocha.game.player;

import com.google.common.eventbus.Subscribe;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

import lombok.extern.java.Log;
import mocha.game.event.MochaEventHandler;
import mocha.game.world.Location;
import mocha.game.world.chunk.Chunk;
import mocha.game.world.chunk.ChunkService;
import mocha.game.world.collision.Collision;
import mocha.game.world.collision.CollisionFactory;
import mocha.game.world.entity.BaseEntity;
import mocha.game.world.entity.Entity;
import mocha.game.world.entity.EntityService;
import mocha.game.world.entity.movement.Movement;
import mocha.game.world.entity.movement.MovementFactory;
import mocha.game.world.entity.prototype.EntityPrototypeService;
import mocha.game.world.item.ItemService;
import mocha.game.world.item.itemprototype.ItemPrototypeService;
import mocha.net.packet.MochaConnection;
import mocha.shared.IdFactory;
import mocha.shared.Repository;

@Log
@Component
public class PlayerJoinedEventHandler implements MochaEventHandler<PlayerJoinedEvent> {

  private Repository<Movement, Integer> movementRepository;
  private MovementFactory movementFactory;
  private ChunkService chunkService;
  private IdFactory<Entity> entityIdFactory;
  private EntityService entityService;
  private PlayerService playerService;
  private ItemService itemService;
  private ItemPrototypeService itemPrototypeService;
  private EntityPrototypeService entityPrototypeService;
  private CollisionFactory collisionFactory;

  @Inject
  public PlayerJoinedEventHandler(Repository<Movement, Integer> movementRepository, MovementFactory movementFactory, ChunkService chunkService, IdFactory<Entity> entityIdFactory, EntityService entityService, PlayerService playerService, ItemService itemService, ItemPrototypeService itemPrototypeService, EntityPrototypeService entityPrototypeService, CollisionFactory collisionFactory) {
    this.movementRepository = movementRepository;
    this.movementFactory = movementFactory;
    this.chunkService = chunkService;
    this.entityIdFactory = entityIdFactory;
    this.entityService = entityService;
    this.playerService = playerService;
    this.itemService = itemService;
    this.itemPrototypeService = itemPrototypeService;
    this.entityPrototypeService = entityPrototypeService;
    this.collisionFactory = collisionFactory;
  }

  @Override
  @Subscribe
  public void handle(PlayerJoinedEvent playerJoinedEvent) {
    log.info(playerJoinedEvent.toString());
    MochaConnection mochaConnection = playerJoinedEvent.getMochaConnection();
    Player player = playerJoinedEvent.getPlayer();
    Integer playerId = player.getId();
    Entity playerEntity = getPlayerEntity(playerId);
    playerService.addEntityToPlayer(playerEntity, player);
    playerInit(mochaConnection, playerId, playerEntity);
  }

  private void playerInit(MochaConnection mochaConnection, Integer playerId, Entity playerEntity) {
    sendEntityPrototypes(mochaConnection);
    sendItemPrototypes(mochaConnection);
    sendItems(mochaConnection);
    sendChunk(mochaConnection, playerEntity);
    sendLoginSuccess(mochaConnection, playerId);
  }

  @NotNull
  private Entity getPlayerEntity(Integer playerId) {
    return playerService.getEntityForPlayerId(playerId).orElseGet(this::newPlayerEntity);
  }

  @NotNull
  private Entity newPlayerEntity() {
    Entity playerEntity = entityService.save(new BaseEntity(entityIdFactory.newId()));
    Collision collision = collisionFactory.newEntityHitBoxCollision(playerEntity);
    playerEntity.setCollision(collision);
    movementRepository.save(movementFactory.newSlidingMovement(playerEntity));
    return playerEntity;
  }

  private void sendEntityPrototypes(MochaConnection playerConnection) {
    entityPrototypeService.findAll().forEach(playerConnection::sendEntityPrototypeUpdate);
  }

  private void sendItemPrototypes(MochaConnection playerConnection) {
    itemPrototypeService.findAll().forEach(playerConnection::sendItemPrototypeUpdate);
  }

  private void sendItems(MochaConnection playerConnection) {
    itemService.findAll().forEach(playerConnection::sendItemUpdate);
  }

  private void sendChunk(MochaConnection playerConnection, Entity playerEntity) {
    Chunk playerChunk = getPlayerChunk(playerEntity);
    playerConnection.sendChunkUpdate(playerChunk);
    sendEntitiesInChunk(playerConnection, playerChunk);
  }

  private void sendEntitiesInChunk(MochaConnection playerConnection, Chunk chunk) {
    entityService.getEntitiesInChunk(chunk)
        .forEach(playerConnection::sendEntityUpdate);
  }

  private void sendLoginSuccess(MochaConnection playerConnection, int playerId) {
    playerConnection.sendLoginSuccessful(playerId);
  }

  private Chunk getPlayerChunk(Entity playerEntity) {
    Location playerEntityLocation = playerEntity.getLocation();
    return chunkService.getOrCreateChunkAt(playerEntityLocation);
  }
}
