package mocha.game.rule;

import com.google.common.collect.Lists;
import com.google.common.eventbus.Subscribe;

import java.util.List;
import java.util.Optional;

import mocha.game.Game;
import mocha.game.world.Location;
import mocha.game.world.chunk.Chunk;
import mocha.game.world.entity.brain.PickUpItemEvent;
import mocha.game.world.tile.Tile;
import mocha.sfx.Sounds;

public class PickUpItemRule implements GameRule {

  private List<PickUpItemEvent> pickups = Lists.newLinkedList();

  @Override
  public void apply(Game game) {
    for (PickUpItemEvent pickUpItemEvent : pickups) {
      Location pickupLocation = pickUpItemEvent.getEntity().getMovement().getLocation();

      Optional<Chunk> maybeChunk = game.getWorld().getChunkAt(pickupLocation);
      if (!maybeChunk.isPresent()) {
        continue;
      }
      Optional<Tile> maybeTile = maybeChunk.get().getTileAt(pickupLocation);
      if (maybeTile.isPresent()) {
        Tile tile = maybeTile.get();

        if (tile.getTileItem() != null) {
          Sounds.ITEM.play();
        }
        tile.setTileItem(null);
      }
    }
    pickups.clear();
  }

  @Subscribe
  public void handleItemPickup(PickUpItemEvent pickUpItemEvent) {
    pickups.add(pickUpItemEvent);
  }

}
