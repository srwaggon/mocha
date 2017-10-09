package mocha.game.world.entity;

import mocha.game.world.entity.movement.InputMomentumMovement;

public class PlayerMob extends Mob {

  private InputMomentumMovement inputMomentumMovement;

  public PlayerMob(InputMomentumMovement inputMomentumMovement) {
    this.inputMomentumMovement = inputMomentumMovement;
    this.movementComponent = this.inputMomentumMovement;
  }

}
