package mocha.game.world.chunk.tile.event;

import lombok.AllArgsConstructor;
import lombok.ToString;
import lombok.Value;
import mocha.game.event.MochaEvent;
import mocha.game.world.chunk.Chunk;

@Value
@AllArgsConstructor
@ToString
public class TileUpdatedEvent implements MochaEvent {

  private Chunk chunk;
  private int x;
  private int y;

}
