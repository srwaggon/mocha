package mocha.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

import mocha.game.player.LocalPlayer;
import mocha.game.player.PlayerService;
import mocha.game.world.Location;
import mocha.game.world.chunk.Chunk;
import mocha.game.world.chunk.tile.TileSetFactory;
import mocha.game.world.entity.Entity;
import mocha.game.world.entity.EntityService;
import mocha.game.world.entity.movement.Movement;
import mocha.game.world.entity.movement.MovementFactory;
import mocha.shared.Repository;

@Component
public class ClientSetup implements CommandLineRunner {

  private TileSetFactory tileSetFactory;
  private Repository<Chunk, Integer> chunkRepository;
  private MovementFactory movementFactory;
  private Repository<Movement, Integer> movementRepository;
  private EntityService entityService;
  private PlayerService playerService;
  private boolean isOnline;

  @Inject
  public ClientSetup(
      TileSetFactory tileSetFactory,
      Repository<Chunk, Integer> chunkRepository,
      MovementFactory movementFactory,
      Repository<Movement, Integer> movementRepository,
      EntityService entityService,
      PlayerService playerService,
      @Value("${mocha.client.online}") boolean isOnline
  ) {
    this.tileSetFactory = tileSetFactory;
    this.chunkRepository = chunkRepository;
    this.movementFactory = movementFactory;
    this.movementRepository = movementRepository;
    this.entityService = entityService;
    this.playerService = playerService;
    this.isOnline = isOnline;
  }

  @Override
  public void run(String... args) {
    if (!isOnline) {
      Location location = new Location(0, 0);
      chunkRepository.save(new Chunk(1, location, tileSetFactory.createRandomTiles()));
      Entity playerEntity = new Entity(1, location);
      LocalPlayer player = new LocalPlayer(0);

      playerService.addPlayer(player);
      Entity entity = entityService.save(playerEntity);
      playerService.addEntityToPlayer(entity, player);

      movementRepository.save(movementFactory.newSlidingMovement(entity));
    }
  }
}
