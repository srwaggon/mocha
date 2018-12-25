package mocha.game.world.entity;

import mocha.game.world.entity.Entity;
import mocha.game.world.entity.movement.RandomMovement;
import mocha.gfx.MochaCanvas;

/**
 * Created by ian on 7/17/17.
 */
public class Foods extends Entity {
    private String flavor = "Chicken";

    public Foods() {
        super();
        RandomMovement randomMovement = new RandomMovement();
        randomMovement.setLocation(this.getLocation());
        this.movementComponent = randomMovement;

    }

    @Override
    public void draw(MochaCanvas mochaCanvas) {
        mochaCanvas.drawSprite(4,getLocation(),2);
    }
}
