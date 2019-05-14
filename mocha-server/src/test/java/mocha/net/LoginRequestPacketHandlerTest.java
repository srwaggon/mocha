package mocha.net;

import org.jetbrains.annotations.NotNull;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import javax.inject.Inject;

import mocha.account.Account;
import mocha.account.AccountNameTakenException;
import mocha.account.AccountService;
import mocha.game.LoginRequestPacket;
import mocha.game.player.Player;
import mocha.game.player.PlayerService;
import mocha.game.player.ServerPlayerJpaRepository;
import mocha.net.packet.MochaConnection;
import mocha.server.event.ServerEventBus;
import mocha.shared.IdFactory;

import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings("OptionalGetWithoutIsPresent")
@Transactional
@SpringBootTest
@RunWith(SpringRunner.class)
public class LoginRequestPacketHandlerTest {

  private LoginRequestPacketHandler loginRequestPacketHandler;

  private String accountName = "link";
  private String emailAddress = "link@hyrule.com";

  @Inject
  private ServerEventBus mochaEventBus;
  @Inject
  private ServerPlayerJpaRepository serverPlayerJpaRepository;
  @Inject
  private PlayerService playerService;
  @Inject
  private AccountService accountService;
  @Inject
  private IdFactory<Player> playerIdFactory;

  @Mock
  private MochaConnection mochaConnection;


  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    loginRequestPacketHandler = new LoginRequestPacketHandler(
        mochaEventBus,
        mochaConnection,
        playerService,
        accountService,
        playerIdFactory
    );
  }

  @Test
  public void handle_createsAPlayerForTheAccount_WhenThePlayerDoesNotExist() throws AccountNameTakenException {
    accountService.createAccount(accountName, emailAddress);
    assertThat(serverPlayerJpaRepository.count()).isEqualTo(0);

    loginRequestPacketHandler.handle(newLoginRequestPacket());

    Optional<Account> accountMaybe = accountService.findAccountByName(accountName);

    assertThat(serverPlayerJpaRepository.count()).isEqualTo(1);
    Player player = serverPlayerJpaRepository.findAll().get(0);
    assertThat(accountMaybe.isPresent()).isTrue();
    assertThat(accountMaybe.get().getPlayer()).isEqualTo(player);
  }

  @NotNull
  private LoginRequestPacket newLoginRequestPacket() {
    return new LoginRequestPacket(accountName);
  }
}