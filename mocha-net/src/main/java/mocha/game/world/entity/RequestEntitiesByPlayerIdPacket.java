package mocha.game.world.entity;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import mocha.net.packet.AbstractPacket;
import mocha.net.packet.PacketType;

public class RequestEntitiesByPlayerIdPacket extends AbstractPacket {

  public RequestEntitiesByPlayerIdPacket() {
  }

  public RequestEntitiesByPlayerIdPacket(int playerId, int... entityIds) {
    data = new String[2 + entityIds.length];
    data[0] = getType().name();
    data[1] = "" + playerId;
    for (int i = 0; i < entityIds.length; i++) {
      data[2 + i] = "" + entityIds[i];
    }
  }

  @Override
  public PacketType getType() {
    return PacketType.REQUEST_ENTITIES_BY_PLAYER_ID;
  }

  public int getPlayerId() {
    return getDataAsInt(1);
  }

  public List<Integer> getEntityIds() {
    return IntStream.range(2, getData().length)
        .mapToObj(this::getDataAsInt)
        .collect(Collectors.toList());
  }
}
