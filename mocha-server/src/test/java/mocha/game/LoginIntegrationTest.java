package mocha.game;

import org.junit.Before;
import org.junit.Test;

import javax.inject.Inject;

import mocha.MochaTest;
import mocha.game.world.Location;
import mocha.game.world.chunk.ChunkService;
import mocha.game.world.entity.Entity;
import mocha.net.exception.DisconnectedException;

import static mocha.game.world.Direction.EAST;
import static mocha.game.world.Direction.NORTH;
import static mocha.game.world.Direction.SOUTH;
import static mocha.game.world.Direction.WEST;
import static org.assertj.core.api.Assertions.assertThat;

public class LoginIntegrationTest extends MochaTest {

  @Inject
  private ChunkService chunkService;

  @Override
  @Before
  public void setUp() throws DisconnectedException {
    super.setUp();

    registerAccount(player1Connection, player1AccountName);
  }

  @Test
  public void playerIdIsPersistedFromPreviousSession_whenReconnecting() throws DisconnectedException {
    Integer playerIdOnConnect = connectPlayer1ToGameServer();
    Integer playerIdOnReconnect = reconnectToGameServer(playerIdOnConnect, player1Connection, player1AccountName);
    assertThat(playerIdOnReconnect).isEqualTo(playerIdOnConnect);
  }

  @Test
  public void newPlayersGetNewPlayerIds() throws DisconnectedException {
    Integer player1Id = connectPlayer1ToGameServer();
    String newAccountName = "newAccount";
    registerAccount(player2Connection, newAccountName);
    Integer newAccountPlayerId = connectToGameServer(player2Connection, newAccountName);
    assertThat(newAccountPlayerId).isNotEqualTo(player1Id);
  }

  @Test
  public void newEntitiesGetNewIdsToo() throws DisconnectedException {
    registerAccount(player1Connection, player1AccountName);
    connectPlayer1ToGameServer();
    Integer entity1Id = getEntityUpdate(player1Connection).getId();

    registerAccount(player2Connection, player2AccountName);
    connectPlayer2ToGameServer();
    Integer entity2Id = getEntityUpdate(player1Connection).getId();

    assertThat(entity1Id).isNotEqualTo(entity2Id);
  }

  @Test
  public void entityIdIsPersistedFromPreviousSession_whenReconnecting() throws DisconnectedException {
    Integer playerId = connectPlayer1ToGameServer();
    Integer entityIdOnConnect = getEntityUpdate(player1Connection).getId();
    disconnectFromGameServer(playerId, player1Connection);
    connectPlayer1ToGameServer();
    Integer entityIdOnReconnect = getEntityUpdate(player1Connection).getId();
    assertThat(entityIdOnReconnect).isEqualTo(entityIdOnConnect);
  }

  @Test
  public void entityLocationIsPersistedFromPreviousSession_whenReconnecting() throws DisconnectedException {
    int playerId = connectPlayer1ToGameServer();
    Entity entity = getEntityUpdate(player1Connection);
    moveEntity(entity, EAST);
    moveEntity(entity, SOUTH);
    Location locationOnDisconnect = entity.getLocation();
    reconnectToGameServer(playerId, player1Connection, player1AccountName);
    Location locationOnReconnect = getEntityUpdate(player1Connection).getLocation();
    assertThat(locationOnReconnect.getX()).isEqualTo(locationOnDisconnect.getX());
    assertThat(locationOnReconnect.getY()).isEqualTo(locationOnDisconnect.getY());
  }

  @Test
  public void entityLocationIsPersistedFromPreviousSession_whenReconnecting_again() throws DisconnectedException {
    int playerId = connectPlayer1ToGameServer();
    Entity entity = getEntityUpdate(player1Connection);
    moveEntity(entity, EAST);
    moveEntity(entity, SOUTH);
    reconnectToGameServer(playerId, player1Connection, player1AccountName);
    moveEntity(entity, NORTH);
    moveEntity(entity, WEST);
    Location locationOnDisconnect = entity.getLocation();
    reconnectToGameServer(playerId, player1Connection, player1AccountName);
    Location locationOnReconnect = getEntityUpdate(player1Connection).getLocation();
    assertThat(locationOnReconnect.getX()).isEqualTo(locationOnDisconnect.getX());
    assertThat(locationOnReconnect.getY()).isEqualTo(locationOnDisconnect.getY());
  }
}