package mocha.game.world.tile.item;

import org.springframework.stereotype.Component;

@Component
public class TileItemFactory {

  public Stone newStone() {
    return new Stone();
  }

  public Food newFood() {
    return new Food();
  }
}
