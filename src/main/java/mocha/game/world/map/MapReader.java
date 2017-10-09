package mocha.game.world.map;

import org.springframework.stereotype.Component;

import javax.inject.Inject;

import mocha.game.world.tile.TileReader;

@Component
public class MapReader {

  @Inject
  private TileReader tileReader;
}
