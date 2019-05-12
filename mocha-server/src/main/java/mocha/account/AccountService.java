package mocha.account;

import org.springframework.stereotype.Service;

import java.util.Optional;

import javax.inject.Inject;

import mocha.game.player.Player;
import mocha.game.player.ServerPlayer;

@Service
public class AccountService {

  private AccountJpaRepository accountJpaRepository;

  @Inject
  public AccountService(
      AccountJpaRepository accountJpaRepository
  ) {
    this.accountJpaRepository = accountJpaRepository;
  }

  public Account createAccount(String name, String emailAddress) throws AccountNameTakenException {
    if (accountJpaRepository.findByName(name).isPresent()) {
      throw new AccountNameTakenException(name);
    }
    return accountJpaRepository.save(new Account(name, emailAddress));
  }

  public Optional<Account> findAccountByName(String name) {
    return accountJpaRepository.findByName(name);
  }

  public Optional<Player> getPlayerId(Account account) {
    return Optional.ofNullable(account.getPlayer());
  }

  public void addPlayer(Account account, ServerPlayer player) {
    account.setPlayer(player);
    accountJpaRepository.save(account);
  }
}
