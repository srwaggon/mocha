package mocha;

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

import mocha.account.AccountService;
import mocha.account.CreateAccountRequestPacket;
import mocha.game.LoginRequestPacket;
import mocha.game.TestGameLoop;
import mocha.game.player.PlayerService;
import mocha.game.world.Direction;
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
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
public class MochaTest {

  @Inject
  protected ConnectedEventHandler connectedEventHandler;
  @Inject
  protected TestGameLoop gameLoop;
  @Inject
  protected PlayerService playerService;
  @Inject
  protected DisconnectedEventHandler disconnectedEventHandler;
  @Inject
  protected EntityMoveCommandHandler entityMoveCommandHandler;
  @Inject
  protected Repository<Movement, Integer> movementRepository;
  @Inject
  protected Repository<Entity, Integer> entityRepository;
  @Inject
  protected AccountService accountService;
  @Mock
  protected MochaConnection player1Connection;
  protected String player1AccountName = "link";
  @Mock
  protected MochaConnection player2Connection;
  protected String player2AccountName = "lonk";

  @Before
  public void setUp() throws DisconnectedException {
    MockitoAnnotations.initMocks(this);

  }

  public void registerAccount(MochaConnection mochaConnection, String accountName) throws DisconnectedException {
    String emailAddress = accountName + "@hyrule.com";
    when(mochaConnection.readPacket()).thenReturn(new CreateAccountRequestPacket(accountName, emailAddress), new LoginRequestPacket(accountName));
    connectedEventHandler.handle(new ConnectedEvent(mochaConnection));
  }

  @After
  public void tearDown() {
    entityRepository.deleteAll();
    movementRepository.deleteAll();
    playerService.deleteAll();
  }

  @Test
  public void contextLoads() {

  }

  public Integer connectToGameServer(MochaConnection mochaConnection, String accountName) throws DisconnectedException {
    when(mochaConnection.readPacket()).thenReturn(new LoginRequestPacket(accountName));
    ArgumentCaptor<Integer> playerIdCaptor = ArgumentCaptor.forClass(Integer.class);
    connectedEventHandler.handle(new ConnectedEvent(mochaConnection));
    verify(mochaConnection, atLeastOnce()).sendLoginSuccessful(playerIdCaptor.capture());
    return playerIdCaptor.getValue();
  }

  public void disconnectFromGameServer(Integer playerIdOnDisconnect, MochaConnection mochaConnection) {
    disconnectedEventHandler.handle(new DisconnectedEvent(playerIdOnDisconnect, mochaConnection));
  }

  public Entity getEntityUpdate(MochaConnection mochaConnection) {
    ArgumentCaptor<Entity> entityCaptor = ArgumentCaptor.forClass(Entity.class);
    verify(mochaConnection, atLeastOnce()).sendEntityUpdate(entityCaptor.capture());
    return entityCaptor.getValue();
  }

  public void moveEntity(Entity entity, Direction direction) {
    entityMoveCommandHandler.handle(buildEntityMoveCommand(entity, direction, true));
    gameLoop.step(40);
    entityMoveCommandHandler.handle(buildEntityMoveCommand(entity, direction, false));
  }

  public Integer reconnectToGameServer(Integer playerIdOnDisconnect, MochaConnection mochaConnection, String accountName) throws DisconnectedException {
    disconnectFromGameServer(playerIdOnDisconnect, mochaConnection);
    return connectToGameServer(mochaConnection, accountName);
  }

  protected void connectPlayer2ToGameServer() throws DisconnectedException {
    connectToGameServer(player2Connection, player2AccountName);
  }

  protected Integer connectPlayer1ToGameServer() throws DisconnectedException {
    return connectToGameServer(player1Connection, player1AccountName);
  }
}
