package mocha.account;

import org.jetbrains.annotations.NotNull;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import javax.inject.Inject;

import mocha.game.LoginRequestPacket;
import mocha.game.player.Player;
import mocha.game.player.ServerPlayer;
import mocha.game.player.ServerPlayerJpaRepository;
import mocha.net.packet.MochaConnection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;


@SuppressWarnings("OptionalGetWithoutIsPresent")
@Transactional
@SpringBootTest
@RunWith(SpringRunner.class)
public class AccountServiceTest {

  @Inject
  private AccountService accountService;

  @Inject
  private AccountJpaRepository accountJpaRepository;

  @Inject
  private ServerPlayerJpaRepository serverPlayerJpaRepository;

  @Mock
  private MochaConnection mochaConnection;

  private String accountName = "link";
  private String emailAddress = "link@hyrule.com";

  @Test
  public void findAccountByName_ReturnsEmpty_WhenAccountDoesNotExist() {
    Optional<Account> accountMaybe = accountService.findAccountByName("link");

    assertThat(accountMaybe).isEmpty();
  }

  @Test
  public void findAccountByName_ReturnsTheRequestedAccount_WhenItExists() {
    Account expectedAccount = accountJpaRepository.save(newAccount(accountName));

    Optional<Account> accountMaybe = accountService.findAccountByName(accountName);

    assertThat(accountMaybe).isPresent();
    Account actualAccount = accountMaybe.get();
    assertThat(actualAccount.getId()).isEqualTo(expectedAccount.getId());
    assertThat(actualAccount.getName()).isEqualTo(expectedAccount.getName());
  }

  @NotNull
  private Account newAccount(String name) {
    return new Account(name, emailAddress);
  }

  @Test
  public void createAccount_CreatesANewAccount() throws AccountNameTakenException {
    assertThat(accountJpaRepository.count()).isEqualTo(0);

    accountService.createAccount(accountName, emailAddress);

    assertThat(accountJpaRepository.count()).isEqualTo(1);
  }

  @Test
  public void createAccount_ThrowsAccountNameTakenExistsException_WhenAccountNameIsUnavailable() {
    AccountNameTakenException expected = new AccountNameTakenException(accountName);
    accountJpaRepository.save(newAccount(accountName));

    assertThatThrownBy(() -> accountService.createAccount(accountName, emailAddress)).isEqualTo(expected);
  }

  @Test
  public void createAccount_ReturnsTheCreatedAccount() throws AccountNameTakenException {
    Account expectedAccount = newAccount(accountName);

    Account account = accountService.createAccount(accountName, emailAddress);

    assertThat(account.getName()).isEqualTo(expectedAccount.getName());
  }

  @Test
  public void addPlayer_SavesThePlayerToTheAccount() throws AccountNameTakenException {
    Account account = accountService.createAccount(accountName, emailAddress);
    ServerPlayer player = new ServerPlayer(23);

    accountService.addPlayer(account, player);

    Optional<Account> accountMaybe = accountJpaRepository.findByName(accountName);
    assertThat(accountMaybe).isPresent();
    assertThat(accountMaybe.get().getPlayer()).isEqualTo(player);
  }

  @Test
  public void getPlayer_ReturnsEmpty_WhenThereIsNoPlayerAssociatedWithTheAccount() {
    Account account = newAccount(accountName);
    Optional<Player> playerIdMaybe = accountService.getPlayer(account);

    assertThat(playerIdMaybe).isEmpty();
  }

  @Test
  public void getPlayer_ReturnsThePlayerIdAssociated_IfItExists() {
    Account account = accountJpaRepository.save(newAccount(accountName));
    ServerPlayer player = serverPlayerJpaRepository.save(new ServerPlayer(25));
    accountService.addPlayer(account, player);

    Optional<Player> playerIdMaybe = accountService.getPlayer(account);

    assertThat(playerIdMaybe).isPresent();
    assertThat(playerIdMaybe.get().getId()).isEqualTo(player.getId());
  }

  @Test
  public void login_DisconnectsTheConnection_WhenTheCredentialsAreBad() {
    accountService.login(mochaConnection, new LoginRequestPacket("unregistered_account"));

    verify(mochaConnection).disconnect();
  }

  @Test
  public void login_ReturnsEmpty_WhenTheCredentialsAreBad() {
    LoginRequestPacket unregisteredAccountPacket = new LoginRequestPacket("unregistered_account");

    Optional<AccountConnection> accountConnectionMaybe = accountService.login(mochaConnection, unregisteredAccountPacket);

    assertThat(accountConnectionMaybe).isEmpty();
  }

  @Test
  public void login_CreatesAndReturnsAnAccountConnection_WhenGivenAConnectionWithValidCredentials() throws AccountNameTakenException {
    accountService.createAccount(accountName, emailAddress);
    LoginRequestPacket loginRequestPacket = new LoginRequestPacket(accountName);

    Optional<AccountConnection> accountConnectionMaybe = accountService.login(mochaConnection, loginRequestPacket);

    assertThat(accountConnectionMaybe).isPresent();
  }

  @Test
  public void login_DoesNotDisconnect_WhenAccountsSuccessfullyAuthenticate() throws AccountNameTakenException {
    accountService.createAccount(accountName, emailAddress);
    LoginRequestPacket loginRequestPacket = new LoginRequestPacket(accountName);

    accountService.login(mochaConnection, loginRequestPacket);

    verify(mochaConnection, never()).disconnect();
  }
}