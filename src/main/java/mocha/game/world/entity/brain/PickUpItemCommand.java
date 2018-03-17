package mocha.game.world.entity.brain;

import lombok.AllArgsConstructor;
import lombok.Data;
import mocha.game.Game;
import mocha.game.command.Command;
import mocha.game.world.entity.Entity;

@Data
@AllArgsConstructor
public class PickUpItemCommand implements Command {
  private Entity pickingUpEntity;

  public void apply(Game game) {
  }
}
