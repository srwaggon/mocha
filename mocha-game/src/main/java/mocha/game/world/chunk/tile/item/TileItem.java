package mocha.game.world.chunk.tile.item;

public abstract class TileItem {
  public abstract boolean blocksMove();

  public abstract TileItemType getTileItemType();

  public abstract int getSpriteId();
}
