package mocha.game.world.entity;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import mocha.game.world.entity.movement.InputMomentumMovement;

@Component
public class PlayerMob extends Mob {

  @Inject
  private InputMomentumMovement inputMomentumMovement;

  public PlayerMob() {
    super();
  }

  public PlayerMob(int id) {
    super(id);
  }

  @Override
  public void tick() {
    super.tick();
  }

  @PostConstruct
  public void init() {
    inputMomentumMovement.setSpeed(getSpeed());
    inputMomentumMovement.setLocation(getLocation());
    this.movementComponent = inputMomentumMovement;
  }
}
