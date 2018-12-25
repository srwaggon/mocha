package mocha.game.world.entity.movement;

import mocha.game.world.Location;

/**
 * Created by ian on 7/17/17.
 */
public class RandomMovement implements Movement {

    private Location location;

    public void setLocation(Location location){
        this.location = location;
    }


    @Override
    public void tick() {
        this.location.setX(this.location.getX() + 1);
    }
}
