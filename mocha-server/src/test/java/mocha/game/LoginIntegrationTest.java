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

import mocha.account.AccountNameTakenException;
import mocha.account.AccountService;
import mocha.game.player.PlayerService;
import mocha.game.world.Direction;
import mocha.game.world.Location;
import mocha.game.world.entity.Entity;
import mocha.game.world.entity.movement.EntityMoveCommandHandler;
import mocha.game.world.entity.movement.Movement;
import mocha.net.ConnectedEventHandler;
import mocha.net.DisconnectedEventHandler;
import mocha.net.event.ConnectedEvent;
import mocha.net.event.DisconnectedEvent;
import mocha.net.exception.DisconnectedException;
import mocha.net.packet.MochaConnection;
import mocha.shared.Repository;

import static mocha.game.world.entity.movement.command.EntityMoveCommandFactory.buildEntityMoveCommand;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
public class LoginIntegrationTest {

  private static final String ACCOUNT_NAME = "link";

  @Inject
  private ConnectedEventHandler connectedEventHandler;

  @Inject
  private TestGameLoop gameLoop;

  @Inject
  private PlayerService playerService;

  @Inject
  private DisconnectedEventHandler disconnectedEventHandler;

  @Inject
  private EntityMoveCommandHandler entityMoveCommandHandler;

  @Inject
  private Repository<Movement, Integer> movementRepository;

  @Inject
  private Repository<Entity, Integer> entityRepository;

  @Inject
  private AccountService accountService;

  @Mock
  private MochaConnection mochaConnection;

  @Before
  public void setUp() throws AccountNameTakenException {
    MockitoAnnotations.initMocks(this);

    accountService.createAccount(ACCOUNT_NAME, "link@hyrule.com");
  }

  @After
  public void tearDown() {
    entityRepository.deleteAll();
    movementRepository.deleteAll();
    playerService.deleteAll();
  }

  @Test
  public void playerIdIsPersistedFromPreviousSession_whenReconnecting() throws DisconnectedException {
    Integer playerIdOnConnect = connectToGameServer();
    Integer playerIdOnReconnect = reconnectToGameServer(playerIdOnConnect);
    assertThat(playerIdOnReconnect).isEqualTo(playerIdOnConnect);
  }

  private Integer connectToGameServer() throws DisconnectedException {
    return connectToGameServer(mochaConnection, ACCOUNT_NAME);
  }

  private Integer connectToGameServer(MochaConnection mochaConnection, String accountName) throws DisconnectedException {
    when(mochaConnection.readPacket()).thenReturn(new LoginRequestPacket(accountName));
    ArgumentCaptor<Integer> playerIdCaptor = ArgumentCaptor.forClass(Integer.class);
    connectedEventHandler.handle(new ConnectedEvent(mochaConnection));
    verify(mochaConnection, atLeastOnce()).sendLoginSuccessful(playerIdCaptor.capture());
    return playerIdCaptor.getValue();
  }

  private Integer reconnectToGameServer(Integer playerIdOnDisconnect) throws DisconnectedException {
    disconnectFromGameServer(playerIdOnDisconnect);
    return connectToGameServer();
  }

  private void disconnectFromGameServer(Integer playerIdOnDisconnect) {
    disconnectedEventHandler.handle(new DisconnectedEvent(playerIdOnDisconnect, mochaConnection));
  }

  @Test
  public void newPlayersGetNewPlayerIds() throws DisconnectedException, AccountNameTakenException {
    Integer player1Id = connectToGameServer(mochaConnection, ACCOUNT_NAME);
    MochaConnection player2Connection = mock(MochaConnection.class);
    String accountName2 = "lonk";
    accountService.createAccount(accountName2, accountName2 + "@hyrule.com");
    Integer player2Id = connectToGameServer(player2Connection, accountName2);
    assertThat(player2Id).isNotEqualTo(player1Id);
  }

  @Test
  public void entityIdIsPersistedFromPreviousSession_whenReconnecting() throws DisconnectedException {
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
  public void entityMovesWhenToldToMove() throws DisconnectedException {
    connectToGameServer();
    Entity entity = getEntityUpdate();
    moveEntity(entity, Direction.EAST);
    moveEntity(entity, Direction.SOUTH);
    Location locationOnDisconnect = entity.getLocation();
    assertThat(locationOnDisconnect).isNotEqualTo(new Location(0, 0));
  }

  @Test
  public void entityLocationIsPersistedFromPreviousSession_whenReconnecting() throws DisconnectedException {
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
    entityMoveCommandHandler.handle(buildEntityMoveCommand(entity, direction, true));
    gameLoop.step(40);
    entityMoveCommandHandler.handle(buildEntityMoveCommand(entity, direction, false));
  }

  @Test
  public void entityLocationIsPersistedFromPreviousSession_whenReconnecting_again() throws DisconnectedException {
    int playerId = connectToGameServer();
    Entity entity = getEntityUpdate();
    moveEntity(entity, Direction.EAST);
    moveEntity(entity, Direction.SOUTH);
    reconnectToGameServer(playerId);
    moveEntity(entity, Direction.NORTH);
    moveEntity(entity, Direction.WEST);
    Location locationOnDisconnect = entity.getLocation();
    reconnectToGameServer(playerId);
    Location locationOnReconnect = getEntityUpdate().getLocation();
    assertThat(locationOnReconnect.getX()).isEqualTo(locationOnDisconnect.getX());
    assertThat(locationOnReconnect.getY()).isEqualTo(locationOnDisconnect.getY());
  }
}