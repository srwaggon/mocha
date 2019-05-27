package mocha.account;

import org.springframework.stereotype.Service;

import java.util.Optional;

import javax.inject.Inject;

import mocha.game.LoginRequestPacket;
import mocha.game.player.Player;
import mocha.game.player.ServerPlayer;
import mocha.net.packet.MochaConnection;

@Service
public class AccountService {

  private AccountJpaRepository accountJpaRepository;

  @Inject
  public AccountService(
      AccountJpaRepository accountJpaRepository
  ) {
    this.accountJpaRepository = accountJpaRepository;
  }

  public Account registerAccount(String name, String emailAddress) throws AccountNameTakenException {
    if (accountJpaRepository.findByName(name).isPresent()) {
      throw new AccountNameTakenException(name);
    }
    return accountJpaRepository.save(new Account(name, emailAddress));
  }

  public Optional<Account> findByName(String name) {
    return accountJpaRepository.findByName(name);
  }

  public Optional<Player> getPlayer(Account account) {
    return Optional.ofNullable(account.getPlayer());
  }

  public void addPlayer(Account account, ServerPlayer player) {
    account.setPlayer(player);
    accountJpaRepository.save(account);
  }

  public Optional<AccountConnection> login(MochaConnection mochaConnection, LoginRequestPacket loginRequestPacket) {
    Optional<Account> accountMaybe = findByName(loginRequestPacket.getAccountName());
    if (!accountMaybe.isPresent()) {
      mochaConnection.disconnect();
      return Optional.empty();
    }
    return Optional.of(new AccountConnection(mochaConnection, accountMaybe.get()));
  }
}
