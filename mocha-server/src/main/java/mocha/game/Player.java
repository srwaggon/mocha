package mocha.game;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import mocha.game.world.entity.Entity;
import mocha.net.packet.MochaConnection;

@Data
@Builder
@AllArgsConstructor
public class Player implements Identified {

  private int id;
  private MochaConnection mochaConnection;
  private Entity entity;

}
