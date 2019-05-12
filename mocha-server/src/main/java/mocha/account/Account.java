package mocha.account;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import mocha.game.player.ServerPlayer;
import mocha.shared.Identified;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Data
@Entity
public class Account implements Identified<String> {

  @Id
  @GeneratedValue(generator = "UUID")
  @GenericGenerator(
      name = "UUID",
      strategy = "org.hibernate.id.UUIDGenerator"
  )
  private String id;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private String emailAddress;

  @JoinColumn
  @OneToOne
  @Cascade(CascadeType.ALL)
  private ServerPlayer player;

  Account(String name, String emailAddress) {
    this.name = name;
    this.emailAddress = emailAddress;
  }

  @Override
  public String getId() {
    return id;
  }

  @Override
  public void setId(String id) {
    this.id = id;
  }
}
