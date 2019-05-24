package mocha.net;

import com.google.common.eventbus.Subscribe;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

import mocha.account.Account;
import mocha.account.AccountConnection;
import mocha.account.AccountNameTakenException;
import mocha.account.AccountService;
import mocha.account.LoginSuccessEvent;
import mocha.game.LoginRequestPacket;
import mocha.game.event.MochaEventHandler;
import mocha.net.event.ConnectedEvent;
import mocha.net.exception.DisconnectedException;
import mocha.net.packet.MochaConnection;
import mocha.server.event.ServerEventBus;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Transactional
@SpringBootTest
@RunWith(SpringRunner.class)
public class ConnectedEventHandlerTest {

  @Mock
  private MochaConnection mochaConnection;

  @Inject
  private ConnectedEventHandler connectedEventHandler;

  @Inject
  private AccountService accountService;

  @Inject
  private ServerEventBus serverEventBus;

  private static final String accountName = "link";
  private static final String emailAddress = "link@hyrule.com";

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
  }

  private ConnectedEvent newConnectedEvent() {
    return new ConnectedEvent(mochaConnection);
  }

  @Test
  public void handle_disconnects_WhenThePacketTypeIsNotLogin() throws DisconnectedException {
    LoginSuccessPacket badPacket = new LoginSuccessPacket(0);
    when(mochaConnection.readPacket()).thenReturn(badPacket);

    connectedEventHandler.handle(newConnectedEvent());

    verify(mochaConnection).disconnect();
  }

  @Test
  public void handle_LogsInTheAccount_WhenALoginRequestPacketIsFound() throws DisconnectedException, AccountNameTakenException {
    accountService.createAccount(accountName, emailAddress);
    LoginRequestPacket loginRequestPacket = new LoginRequestPacket(accountName);
    when(mochaConnection.readPacket()).thenReturn(loginRequestPacket);

    final AccountConnection[] accountConnectionWrapper = new AccountConnection[1];
    serverEventBus.register(new MochaEventHandler<LoginSuccessEvent>() {
      @Override
      @Subscribe
      public void handle(LoginSuccessEvent loginSuccessEvent) {
        accountConnectionWrapper[0] = loginSuccessEvent.getAccountConnection();
      }
    });

    connectedEventHandler.handle(newConnectedEvent());

    AccountConnection value = accountConnectionWrapper[0];
    assertThat(value).isNotNull();
    Account account = value.getAccount();
    assertThat(account.getName()).isEqualTo(accountName);
    assertThat(account.getEmailAddress()).isEqualTo(emailAddress);
    assertThat(value.getMochaConnection()).isEqualTo(mochaConnection);
  }
}