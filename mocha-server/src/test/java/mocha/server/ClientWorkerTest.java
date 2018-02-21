package mocha.server;

import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import mocha.game.Game;
import mocha.game.world.Location;
import mocha.game.world.World;
import mocha.game.world.chunk.Chunk;
import mocha.game.world.entity.Entity;
import mocha.game.world.tile.TileType;
import mocha.net.MochaConnection;
import mocha.net.packet.Packet;
import mocha.net.packet.PacketType;
import mocha.net.packet.world.chunk.ChunkRequestPacket;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;


@RunWith(MockitoJUnitRunner.class)
public class ClientWorkerTest {

  private ClientWorker testObject;
  @Mock private MochaConnection mochaConnection;
  @Captor private ArgumentCaptor<Packet> packetArgumentCaptor;

  @Before
  public void setUp() {
    Location location = new Location(0, 0);
    List<Entity>[] tileEntities = new List[0];
    Chunk chunk = new Chunk(new TileType[0], tileEntities);
    World world = new World();
    world.getChunks().put(location, chunk);
    Game game = new Game(world, Lists.emptyList());

    testObject = new ClientWorker(mochaConnection, game);
  }

  @Test
  public void handlePacket_SendsAChunkUpdate_WhenAChunkIsRequested() {
    ChunkRequestPacket chunkRequestPacket = new ChunkRequestPacket(new Location(0, 0));

    testObject.handlePacket(chunkRequestPacket);

    verify(mochaConnection).sendPacket(packetArgumentCaptor.capture());
    Packet capturedPacket = packetArgumentCaptor.getValue();
    assertThat(capturedPacket.getType()).isEqualTo(PacketType.CHUNK);
    // TODO
//    assertThat(capturedPacket.getData()[1]).isEqualTo("")
  }
}