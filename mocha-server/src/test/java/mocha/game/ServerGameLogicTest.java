package mocha.game;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.inject.Inject;

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
import static org.mockito.Mockito.verify;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ServerGameLogicTest {

  @Inject
  private ServerGameLogic serverGameLogic;

  @Inject
  private TestGameLoop gameLoop;

  @Mock
  private MochaConnection mochaConnection;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void playerIdIsPersistedFromPreviousSession_whenReconnecting() {
    serverGameLogic.handle(new ConnectedEvent(mochaConnection));
    ArgumentCaptor<Integer> firstConnectCaptor = ArgumentCaptor.forClass(Integer.class);
    verify(mochaConnection).sendLoginSuccessful(firstConnectCaptor.capture());
    Integer playerIdOnDisconnect = firstConnectCaptor.getValue();

    serverGameLogic.handle(new DisconnectedEvent(firstConnectCaptor.getValue(), mochaConnection));

    serverGameLogic.handle(new ConnectedEvent(mochaConnection));
    ArgumentCaptor<Integer> reconnectCaptor = ArgumentCaptor.forClass(Integer.class);
    verify(mochaConnection).sendLoginSuccessful(reconnectCaptor.capture());
    Integer playerIdOnReconnect = firstConnectCaptor.getValue();

    assertThat(playerIdOnReconnect).isEqualTo(playerIdOnDisconnect);
  }

  @Test
  public void entityIdIsPersistedFromPreviousSession_whenReconnecting() {
    ArgumentCaptor<Entity> entityCaptor = ArgumentCaptor.forClass(Entity.class);
    serverGameLogic.handle(new ConnectedEvent(mochaConnection));

    verify(mochaConnection, atLeastOnce()).sendEntityUpdate(entityCaptor.capture());
    Entity entity = entityCaptor.getValue();
    Integer entityIdOnDisconnect = entity.getId();

    ArgumentCaptor<Integer> playerIdCaptor = ArgumentCaptor.forClass(Integer.class);
    verify(mochaConnection).sendLoginSuccessful(playerIdCaptor.capture());
    serverGameLogic.handle(new DisconnectedEvent(playerIdCaptor.getValue(), mochaConnection));

    entityCaptor = ArgumentCaptor.forClass(Entity.class);
    serverGameLogic.handle(new ConnectedEvent(mochaConnection));
    verify(mochaConnection, atLeastOnce()).sendEntityUpdate(entityCaptor.capture());
    entity = entityCaptor.getValue();
    Integer entityIdOnReconnect = entity.getId();
    assertThat(entityIdOnReconnect).isEqualTo(entityIdOnDisconnect);
  }

  @Test
  public void entityLocationIsPersistedFromPreviousSession_whenReconnecting() {
    ArgumentCaptor<Entity> entityCaptor = ArgumentCaptor.forClass(Entity.class);
    serverGameLogic.handle(new ConnectedEvent(mochaConnection));

    verify(mochaConnection, atLeastOnce()).sendEntityUpdate(entityCaptor.capture());
    Entity entity = entityCaptor.getValue();
    serverGameLogic.handle(buildEntityStartMoveCommand(entity, Direction.EAST));
    gameLoop.step(40);
    serverGameLogic.handle(buildEntityStopMoveCommand(entity, Direction.EAST));
    Location locationOnDisconnect = entity.getLocation();
    System.out.println(entity);
    assertThat(locationOnDisconnect).isNotEqualTo(new Location(0, 0));

    ArgumentCaptor<Integer> playerIdCaptor = ArgumentCaptor.forClass(Integer.class);
    verify(mochaConnection).sendLoginSuccessful(playerIdCaptor.capture());
    serverGameLogic.handle(new DisconnectedEvent(playerIdCaptor.getValue(), mochaConnection));

    entityCaptor = ArgumentCaptor.forClass(Entity.class);
    serverGameLogic.handle(new ConnectedEvent(mochaConnection));
    verify(mochaConnection, atLeastOnce()).sendEntityUpdate(entityCaptor.capture());
    entity = entityCaptor.getValue();
    Location locationOnReconnect = entity.getLocation();
    assertThat(locationOnReconnect.getX()).isEqualTo(locationOnDisconnect.getX());
    assertThat(locationOnReconnect.getY()).isEqualTo(locationOnDisconnect.getY());
  }
}