package mocha.game.world.chunk;

import java.util.Arrays;
import java.util.stream.Collectors;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import mocha.game.world.chunk.tile.TileType;

@Converter
public class TileTypeArrayConverter implements AttributeConverter<TileType[], String> {

  @Override
  public String convertToDatabaseColumn(TileType[] tiles) {
    return Arrays.stream(tiles)
        .map(TileType::getSymbol)
        .map(String::valueOf)
        .collect(Collectors.joining());
  }

  @Override
  public TileType[] convertToEntityAttribute(String tileString) {
    int rows = Chunk.SIZE;
    int columns = Chunk.SIZE;
    TileType[] tiles = new TileType[rows * columns];
    for (int i = 0; i < tileString.length(); i++) {
      int x = i % columns;
      int y = i / columns;
      char tileTypeSymbol = tileString.charAt(i);
      tiles[y * rows + x] = TileType.valueOf(tileTypeSymbol);
    }
    return tiles;
  }

}