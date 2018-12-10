package mocha.game.world.chunk.tile;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TileConfiguration {

  @Bean
  public TileReader tileReader() {
    return new TileReader();
  }

  @Bean
  public TileStringBuilder tileStringBuilder() {
    return new TileStringBuilder();
  }

  @Bean
  public TileSetFactory tileSetFactory(TileReader tileReader) {
    return new TileSetFactory(tileReader);
  }

}
