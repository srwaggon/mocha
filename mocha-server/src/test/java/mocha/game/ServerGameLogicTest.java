package mocha.game;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

import mocha.game.player.PlayerService;
import mocha.game.world.Direction;
import mocha.game.world.Location;
import mocha.game.world.entity.Entity;
import mocha.net.event.ConnectedEvent;
import mocha.net.event.DisconnectedEvent;
import mocha.net.packet.MochaConnection;

import static mocha.game.world.entity.movement.command.EntityMoveCommandFactory.buildEntityStartMoveCommand;
import static mocha.game.world.entity.movement.command.EntityMoveCommandFactory.buildEntityStopMoveCommand;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
public class ServerGameLogicTest {

  @Inject
  private ServerGameLogic serverGameLogic;

  @Inject
  private TestGameLoop gameLoop;

  @Inject
  private PlayerService playerService;

  @Mock
  private MochaConnection mochaConnection;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
  }

  @After
  public void tearDown() {
    playerService.deleteAll();
  }

  @Test
  public void playerIdIsPersistedFromPreviousSession_whenReconnecting() {
    Integer playerIdOnConnect = connectToGameServer();
    Integer playerIdOnReconnect = reconnectToGameServer(playerIdOnConnect);
    assertThat(playerIdOnReconnect).isEqualTo(playerIdOnConnect);
  }

  private Integer connectToGameServer() {
    return connectToGameServer(mochaConnection);
  }

  private Integer connectToGameServer(MochaConnection mochaConnection) {
    ArgumentCaptor<Integer> playerIdCaptor = ArgumentCaptor.forClass(Integer.class);
    serverGameLogic.handle(new ConnectedEvent(mochaConnection));
    verify(mochaConnection, atLeastOnce()).sendLoginSuccessful(playerIdCaptor.capture());
    return playerIdCaptor.getValue();
  }

  private Integer reconnectToGameServer(Integer playerIdOnDisconnect) {
    disconnectFromGameServer(playerIdOnDisconnect);
    return connectToGameServer();
  }

  private void disconnectFromGameServer(Integer playerIdOnDisconnect) {
    serverGameLogic.handle(new DisconnectedEvent(playerIdOnDisconnect, mochaConnection));
  }

  @Test
  public void newPlayersGetNewPlayerIds() {
    Integer player1Id = connectToGameServer(mochaConnection);
    MochaConnection player2Connection = mock(MochaConnection.class);
    Integer player2Id = connectToGameServer(player2Connection);
    assertThat(player2Id).isNotEqualTo(player1Id);
  }

  @Test
  public void entityIdIsPersistedFromPreviousSession_whenReconnecting() {
    Integer playerId = connectToGameServer();
    Integer entityIdOnConnect = getEntityUpdate().getId();
    disconnectFromGameServer(playerId);
    connectToGameServer();
    Integer entityIdOnReconnect = getEntityUpdate().getId();
    assertThat(entityIdOnReconnect).isEqualTo(entityIdOnConnect);
  }

  private Entity getEntityUpdate() {
    ArgumentCaptor<Entity> entityCaptor = ArgumentCaptor.forClass(Entity.class);
    verify(mochaConnection, atLeastOnce()).sendEntityUpdate(entityCaptor.capture());
    return entityCaptor.getValue();
  }

  @Test
  public void entityMovesWhenToldToMove() {
    connectToGameServer();
    Entity entity = getEntityUpdate();
    moveEntity(entity, Direction.EAST);
    moveEntity(entity, Direction.SOUTH);
    Location locationOnDisconnect = entity.getLocation();
    assertThat(locationOnDisconnect).isNotEqualTo(new Location(0, 0));
  }

  @Test
  public void entityLocationIsPersistedFromPreviousSession_whenReconnecting() {
    int playerId = connectToGameServer();
    Entity entity = getEntityUpdate();
    moveEntity(entity, Direction.EAST);
    moveEntity(entity, Direction.SOUTH);
    Location locationOnDisconnect = entity.getLocation();
    reconnectToGameServer(playerId);
    Location locationOnReconnect = getEntityUpdate().getLocation();
    assertThat(locationOnReconnect.getX()).isEqualTo(locationOnDisconnect.getX());
    assertThat(locationOnReconnect.getY()).isEqualTo(locationOnDisconnect.getY());
  }

  private void moveEntity(Entity entity, Direction direction) {
    serverGameLogic.handle(buildEntityStartMoveCommand(entity, direction));
    gameLoop.step(40);
    serverGameLogic.handle(buildEntityStopMoveCommand(entity, direction));
  }
}