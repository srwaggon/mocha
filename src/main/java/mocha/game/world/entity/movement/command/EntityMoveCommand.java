package mocha.game.world.entity.movement.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import mocha.game.command.Command;
import mocha.game.world.Direction;
import mocha.game.world.Location;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EntityMoveCommand implements Command {
  private int entityId;
  private Location location;
  private Direction direction;
  private int xOffset;
  private int yOffset;
}
