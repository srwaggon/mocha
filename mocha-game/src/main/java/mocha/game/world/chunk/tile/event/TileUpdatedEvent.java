package mocha.game.world.chunk.tile.event;

import lombok.AllArgsConstructor;
import lombok.ToString;
import lombok.Value;
import mocha.game.event.MochaEvent;
import mocha.game.world.chunk.tile.TileType;

@Value
@AllArgsConstructor
@ToString
public class TileUpdatedEvent implements MochaEvent {

  private int x;
  private int y;
  private TileType tileType;

}
