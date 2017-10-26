package mocha.game.world.tile.item;

import lombok.Data;

@Data
public class Food extends TileItem {

  private boolean isConsumed;

  @Override
  public boolean blocksMove() {
    return false;
  }

  @Override
  public TileItemType getTileItemType() {
    return TileItemType.FOOD;
  }

  @Override
  public int getSpriteId() {
    return 11;
  }
}
