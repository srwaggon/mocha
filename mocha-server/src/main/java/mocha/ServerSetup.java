package mocha;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Random;

import javax.inject.Inject;

import mocha.game.Game;
import mocha.game.item.ItemPrototypeRepository;
import mocha.game.item.ItemRepository;
import mocha.game.item.ServerItem;
import mocha.game.item.ServerItemPrototype;
import mocha.game.world.Location;
import mocha.game.world.chunk.Chunk;
import mocha.game.world.chunk.ChunkService;
import mocha.game.world.entity.Entity;
import mocha.game.world.item.ItemType;
import mocha.game.world.tile.TileSetFactory;
import mocha.shared.Repository;

@Component
public class ServerSetup implements CommandLineRunner {

  @Inject
  private Game game;

  @Inject
  private Repository<Chunk, Integer> chunkRepository;

  @Inject
  private TileSetFactory tileSetFactory;

  @Inject
  private ChunkService chunkService;

  @Inject
  private ItemRepository itemRepository;
  @Inject
  private ItemPrototypeRepository itemPrototypeRepository;

  @Override
  public void run(String... args) {
    createItems();
    createChunks();
  }

  private void createItems() {
    ServerItemPrototype itemPrototype = new ServerItemPrototype(1, "Pickaxe", 100, ItemType.TOOL, "cool");
    ServerItemPrototype pickaxe = itemPrototypeRepository.save(itemPrototype);

    ServerItem item = new ServerItem(1, itemPrototype, 0, 0, 0);
    itemRepository.save(item);
    itemRepository.count();
  }

  private void addEntity() {
    game.addEntity(newRandomEntity());
  }

  private Entity newRandomEntity() {
    return new Entity(null, new Location(random(), random()));
  }

  private int random() {
    return new Random().nextInt(16) * 32;
  }

  private void createChunks() {
    int radius = 0;
    for (int y = -radius; y <= radius; y++) {
      for (int x = -radius; x <= radius; x++) {
        chunkService.getOrCreateChunkByIndex(new Location(x, y));
      }
    }
  }
}
