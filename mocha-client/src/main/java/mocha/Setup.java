package mocha;

import com.google.common.collect.Sets;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

import mocha.game.Game;
import mocha.game.LocalPlayer;
import mocha.game.world.chunk.Chunk;
import mocha.game.world.entity.Entity;
import mocha.game.world.entity.EntityFactory;
import mocha.game.world.tile.TileSetFactory;
import mocha.shared.Repository;

@Component
public class Setup implements CommandLineRunner {

  @Inject
  private Game game;

  @Inject
  private TileSetFactory tileSetFactory;

  @Inject
  private Repository<Chunk, Integer> chunkRepository;

  @Inject
  private EntityFactory entityFactory;

  @Value("${mocha.client.online}")
  private boolean isOnline;

  @Override
  public void run(String... args) {

    chunkRepository.save(new Chunk(1, tileSetFactory.createRandomTiles(), Sets.newHashSet()));

    if (!isOnline) {
      Entity playerEntity = entityFactory.newSlider();
      LocalPlayer player = new LocalPlayer(playerEntity);
      game.addPlayer(player);
      game.addEntity(playerEntity);
    }
  }
}
