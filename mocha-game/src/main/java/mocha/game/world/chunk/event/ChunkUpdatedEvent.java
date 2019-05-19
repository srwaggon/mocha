package mocha.game.world.chunk.event;

import lombok.AllArgsConstructor;
import lombok.ToString;
import lombok.Value;
import mocha.game.event.MochaEvent;
import mocha.game.world.chunk.Chunk;

@AllArgsConstructor
@Value
@ToString
public class ChunkUpdatedEvent implements MochaEvent {

  private final Chunk chunk;

}
