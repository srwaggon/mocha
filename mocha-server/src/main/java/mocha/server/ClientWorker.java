package mocha.server;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import mocha.game.Identified;
import mocha.game.world.entity.Entity;
import mocha.net.packet.MochaConnection;
import mocha.net.packet.PacketListener;

@Data
@Builder
@AllArgsConstructor
public class ClientWorker implements Identified {

  private int id;
  private MochaConnection mochaConnection;
  private Entity entity;
  private PacketListener packetListener;
  private ServerPacketHandler serverPacketHandler;

}
