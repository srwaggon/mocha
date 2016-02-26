package mocha.net.packet;

import org.junit.Before;
import org.junit.Test;

import mocha.game.world.Map;

import static org.junit.Assert.*;

public class MapPacketTest {

  private MapPacket subject;

  private Map testMap;

  @Before
  public void setUp() {
    testMap = new Map(10, 11, 12);

    subject = new MapPacket(testMap);
  }

  @Test
  public void getCode_ReturnsMapCode() {
    assertEquals(PacketType.MAP, subject.getType());
  }

  @Test
  public void construct_ContainsMapCode_AsFirstPhrase() {
    assertEquals(PacketType.MAP.name(), subject.getData()[0]);
    assertEquals(PacketType.MAP.name(), getPhrase(0));
  }

  @Test
  public void construct_ContainsMapId_AsSecondPhrase() {
    int index = 1;

    assertEquals(testMap.getId(), Integer.parseInt(subject.getData()[index]));
    assertEquals(testMap.getId(), getPhraseAsInt(index));
  }

  @Test
  public void construct_ContainsColumnCount_AsThirdPhrase() {
    int index = 2;

    assertEquals(testMap.getColumnCount(), Integer.parseInt(subject.getData()[index]));
    assertEquals(testMap.getColumnCount(), getPhraseAsInt(index));
  }

  @Test
  public void construct_ContainsRowCount_AsFourthPhrase() {
    int index = 3;

    assertEquals(testMap.getRowCount(), Integer.parseInt(subject.getData()[index]));
    assertEquals(testMap.getRowCount(), getPhraseAsInt(index));
  }

  private String getPhrase(int index) {
    return subject.construct().split(PacketType.SEPARATOR)[index];
  }

  private int getPhraseAsInt(int index) {
    return Integer.parseInt(getPhrase(index));
  }


}