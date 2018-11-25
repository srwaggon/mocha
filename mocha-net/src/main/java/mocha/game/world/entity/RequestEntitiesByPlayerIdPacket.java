package mocha.game.world.entity;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import mocha.net.packet.AbstractPacket;
import mocha.net.packet.PacketType;

public class RequestEntitiesByPlayerIdPacket extends AbstractPacket {

  public RequestEntitiesByPlayerIdPacket() {
  }

  public RequestEntitiesByPlayerIdPacket(int playerId, int... entityIds) {
    addToData(playerId);
    Arrays.stream(entityIds).forEach(this::addToData);
  }

  @Override
  public PacketType getType() {
    return PacketType.REQUEST_ENTITIES_BY_PLAYER_ID;
  }

  public int getPlayerId() {
    return getDataAsInt(1);
  }

  public List<Integer> getEntityIds() {
    return IntStream.range(2, getSplitData().length)
        .mapToObj(this::getDataAsInt)
        .collect(Collectors.toList());
  }
}
