package mocha.account;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class AccountNameTakenException extends Exception {

  public AccountNameTakenException(String name) {
    super(String.format("Could not create account with name %s: account with name already exists.", name));
  }
}
