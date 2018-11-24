package mocha.game.world.entity.rule;


import com.google.common.collect.Lists;
import com.google.common.eventbus.Subscribe;

import java.util.Queue;

import mocha.game.Game;
import mocha.game.rule.GameRule;
import mocha.game.world.Location;
import mocha.game.world.chunk.Chunk;
import mocha.game.world.chunk.ChunkService;
import mocha.game.world.entity.Entity;
import mocha.game.world.entity.PickUpItemCommand;
import mocha.shared.Repository;

public class PickUpItemsRule implements GameRule {

  private Repository<Chunk, Integer> chunkRepository;
  private ChunkService chunkService;

  private Queue<PickUpItemCommand> pickUpItemCommands = Lists.newLinkedList();

  public PickUpItemsRule(Repository<Chunk, Integer> chunkRepository, ChunkService chunkService) {
    this.chunkRepository = chunkRepository;
    this.chunkService = chunkService;
  }

  @Subscribe
  public void handlePickUpItemCommand(PickUpItemCommand pickUpItemCommand) {
    pickUpItemCommands.add(pickUpItemCommand);
  }

  @Override
  public void apply(Game game) {
    while (!pickUpItemCommands.isEmpty()) {
      pickUpItem(game, pickUpItemCommands.poll());
    }
  }

  private void pickUpItem(Game game, PickUpItemCommand pickUpItemCommand) {
    Location location = pickUpItemCommand.getPickingUpEntity().getLocation();
    Chunk chunk = chunkService.getChunkAt(location);
    removeEntity(game, pickUpItemCommand.getPickingUpEntity(), chunk);
  }

  private void removeEntity(Game game, Entity pickingUpEntity, Chunk chunk) {
    chunk.getEntitiesAt(pickingUpEntity.getLocation()).stream()
        .filter(entity -> entity.getId() != pickingUpEntity.getId())
        .findFirst()
        .ifPresent(game::removeEntity);
  }
}
