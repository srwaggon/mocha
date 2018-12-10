package mocha.game.world.chunk.tile.item;

public class Stone extends TileItem {
  @Override
  public boolean blocksMove() {
    return true;
  }

  @Override
  public TileItemType getTileItemType() {
    return TileItemType.OTHER;
  }

  @Override
  public int getSpriteId() {
    return 4;
  }
}
