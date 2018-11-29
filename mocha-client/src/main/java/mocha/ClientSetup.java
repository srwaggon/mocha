package mocha;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

import mocha.game.Game;
import mocha.game.LocalPlayer;
import mocha.game.world.Location;
import mocha.game.world.chunk.Chunk;
import mocha.game.world.entity.Entity;
import mocha.game.world.entity.movement.Movement;
import mocha.game.world.entity.movement.MovementFactory;
import mocha.game.world.tile.TileSetFactory;
import mocha.shared.Repository;

@Component
public class ClientSetup implements CommandLineRunner {

  @Inject
  private Game game;

  @Inject
  private TileSetFactory tileSetFactory;

  @Inject
  private Repository<Chunk, Integer> chunkRepository;

  @Inject
  private MovementFactory movementFactory;

  @Inject
  private Repository<Movement, Integer> movementRepository;

  @Value("${mocha.client.online}")
  private boolean isOnline;

  @Override
  public void run(String... args) {
    if (!isOnline) {
      Location location = new Location(0, 0);
      chunkRepository.save(new Chunk(1, location, tileSetFactory.createRandomTiles()));
      Entity playerEntity = new Entity(1, location);
      LocalPlayer player = new LocalPlayer(playerEntity);

      game.addPlayer(player);
      Entity entity = game.addEntity(playerEntity);

      movementRepository.save(movementFactory.newSlidingMovement(entity));
    }
  }
}
