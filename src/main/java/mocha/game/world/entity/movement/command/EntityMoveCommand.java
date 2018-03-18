package mocha.game.world.entity.movement.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import mocha.game.command.Command;
import mocha.game.world.Direction;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EntityMoveCommand implements Command {
  private int entityId;
  private Direction direction;
  private int xOffset;
  private int yOffset;
}
