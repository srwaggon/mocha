package mocha.game.world.entity.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import mocha.game.world.entity.Entity;

@Data
@AllArgsConstructor
public class RemoveEntityCommand {
  private Entity entity;
}
