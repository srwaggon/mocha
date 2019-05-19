package mocha.game.world.entity.event;

import lombok.AllArgsConstructor;
import lombok.ToString;
import lombok.Value;
import mocha.game.event.MochaEvent;
import mocha.game.world.entity.Entity;

@AllArgsConstructor
@Value
@ToString
public class EntityAddedEvent implements MochaEvent {

  private final Entity entity;

}
