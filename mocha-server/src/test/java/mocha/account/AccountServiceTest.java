package mocha.account;

import org.jetbrains.annotations.NotNull;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import javax.inject.Inject;

import mocha.game.player.Player;
import mocha.game.player.ServerPlayer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


@SuppressWarnings("OptionalGetWithoutIsPresent")
@Transactional
@SpringBootTest
@RunWith(SpringRunner.class)
public class AccountServiceTest {

  @Inject
  private AccountService accountService;

  @Inject
  private AccountJpaRepository accountJpaRepository;
  private String name = "link";
  private String emailAddress = "link@hyrule.com";

  @Test
  public void findAccountByName_ReturnsEmpty_WhenAccountDoesNotExist() {
    Optional<Account> accountMaybe = accountService.findAccountByName("link");

    assertThat(accountMaybe).isEqualTo(Optional.empty());
  }

  @Test
  public void findAccountByName_ReturnsTheRequestedAccount_WhenItExists() {
    Account expectedAccount = accountJpaRepository.save(newAccount(name));

    Optional<Account> accountMaybe = accountService.findAccountByName(name);

    assertThat(accountMaybe.isPresent()).isTrue();
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

    accountService.createAccount(name, emailAddress);

    assertThat(accountJpaRepository.count()).isEqualTo(1);
  }

  @Test
  public void createAccount_ThrowsAccountNameTakenExistsException_WhenAccountNameIsUnavailable() {
    AccountNameTakenException expected = new AccountNameTakenException(name);
    accountJpaRepository.save(newAccount(name));

    assertThatThrownBy(() -> accountService.createAccount(name, emailAddress)).isEqualTo(expected);
  }

  @Test
  public void createAccount_ReturnsTheCreatedAccount() throws AccountNameTakenException {
    Account expectedAccount = newAccount(name);

    Account account = accountService.createAccount(name, emailAddress);

    assertThat(account.getName()).isEqualTo(expectedAccount.getName());
  }

  @Test
  public void addPlayer_SavesThePlayerToTheAccount() throws AccountNameTakenException {
    Account account = accountService.createAccount(name, emailAddress);
    ServerPlayer player = new ServerPlayer(23);

    accountService.addPlayer(account, player);

    Optional<Account> accountMaybe = accountJpaRepository.findByName(name);
    assertThat(accountMaybe.isPresent()).isTrue();
    Account actual = accountMaybe.get();
    assertThat(actual.getPlayer()).isEqualTo(player);
  }

  @Test
  public void getPlayerId_ReturnsEmpty_WhenThereIsNoPlayerAssociatedWithTheAccount() {
    Account account = newAccount(name);
    Optional<Player> playerIdMaybe = accountService.getPlayerId(account);

    assertThat(playerIdMaybe).isEqualTo(Optional.empty());
  }

//  @Test
//  public void getPlayerId_ReturnsThePlayerIdAssociated_IfItExists() {
//
//  }
}