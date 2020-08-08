package mocha.game.event;

import com.google.common.eventbus.EventBus;

import mocha.game.player.Player;
import mocha.game.player.event.PlayerAddedEvent;
import mocha.game.player.event.PlayerRemovedEvent;
import mocha.game.world.Location;
import mocha.game.world.chunk.Chunk;
import mocha.game.world.chunk.event.ChunkUpdatedEvent;
import mocha.game.world.chunk.tile.TileType;
import mocha.game.world.chunk.tile.event.TileUpdatedEvent;
import mocha.game.world.entity.Entity;
import mocha.game.world.entity.command.PickUpItemCommand;
import mocha.game.world.entity.command.RemoveEntityCommand;
import mocha.game.world.entity.event.EntityAddedEvent;
import mocha.game.world.entity.event.EntityRemovedEvent;
import mocha.game.world.entity.event.EntityUpdatedEvent;
import mocha.game.world.entity.movement.command.EntityMoveCommand;
import mocha.game.world.entity.movement.event.EntityMovementEvent;
import mocha.shared.task.event.TaskEvent;

public class MochaEventBus extends EventBus {

  public void postPlayerAddedEvent(Player player) {
    post(new PlayerAddedEvent(player));
  }

  public void postPlayerRemovedEvent(Player player) {
    post(new PlayerRemovedEvent(player));
  }

  public void postEntityAddedEvent(Entity entity) {
    post(new EntityAddedEvent(entity));
  }

  public void postEntityUpdatedEvent(Entity entity) {
    post(new EntityUpdatedEvent(entity));
  }

  public void postChunkUpdatedEvent(Chunk chunk) {
    post(new ChunkUpdatedEvent(chunk));
  }

  public void postTileUpdatedEvent(Location location, TileType tileType) {
    post(new TileUpdatedEvent(location.getX(), location.getY(), tileType));
  }

  public void postMoveCommand(EntityMoveCommand entityMoveCommand) {
    post(entityMoveCommand);
  }

  public void postMoveEvent(EntityMoveCommand entityMoveCommand) {
    post(new EntityMovementEvent(entityMoveCommand));
  }

  public void postRemoveEntityCommand(Entity entity) {
    post(new RemoveEntityCommand(entity));
  }

  public void postEntityRemovedEvent(Entity entity) {
    post(new EntityRemovedEvent(entity));
  }

  public void postPickUpItemCommand(Entity entity) {
    post(new PickUpItemCommand(entity));
  }

  public void postTaskEvent(Runnable runnable) {
    post(new TaskEvent(runnable));
  }

}
