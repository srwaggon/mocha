package mocha.net;

import org.springframework.stereotype.Component;

import java.util.Map;

import javax.inject.Inject;

import mocha.game.player.PlayerService;
import mocha.game.world.chunk.ChunkService;
import mocha.game.world.entity.EntitiesInChunkService;
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

@Component
public class LoginRequestPacketHandlerFactory {

  private final Repository<Movement, Integer> movementRepository;
  private final MovementFactory movementFactory;
  private final EntitiesInChunkService entitiesInChunkService;
  private final ChunkService chunkService;
  private final IdFactory<Entity> entityIdFactory;
  private final EntityService entityService;
  private final PlayerService playerService;
  private final ItemService itemService;
  private final ItemPrototypeService itemPrototypeService;
  private final EntityPrototypeService entityPrototypeService;
  private final Map<Integer, MochaConnection> mochaConnectionsByPlayerId;

  @Inject
  public LoginRequestPacketHandlerFactory(
      Repository<Movement, Integer> movementRepository,
      MovementFactory movementFactory,
      EntitiesInChunkService entitiesInChunkService,
      ChunkService chunkService,
      IdFactory<Entity> entityIdFactory,
      EntityService entityService,
      PlayerService playerService,
      ItemService itemService,
      ItemPrototypeService itemPrototypeService,
      EntityPrototypeService entityPrototypeService,
      Map<Integer, MochaConnection> mochaConnectionsByPlayerId
  ) {
    this.movementRepository = movementRepository;
    this.movementFactory = movementFactory;
    this.entitiesInChunkService = entitiesInChunkService;
    this.chunkService = chunkService;
    this.entityIdFactory = entityIdFactory;
    this.entityService = entityService;
    this.playerService = playerService;
    this.itemService = itemService;
    this.itemPrototypeService = itemPrototypeService;
    this.entityPrototypeService = entityPrototypeService;
    this.mochaConnectionsByPlayerId = mochaConnectionsByPlayerId;
  }

  public LoginRequestPacketHandler newLoginRequestPacketHandler(
      MochaConnection mochaConnection
  ) {
    return new LoginRequestPacketHandler(mochaConnection, movementRepository, movementFactory, entitiesInChunkService, chunkService, entityIdFactory, entityService, playerService, itemService, itemPrototypeService, entityPrototypeService, mochaConnectionsByPlayerId);
  }
}
