package mocha;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

import mocha.game.Game;
import mocha.game.world.entity.EntityFactory;

@Component
public class Setup implements CommandLineRunner {

  @Inject
  private Game game;

  @Inject
  private EntityFactory entityFactory;
  @Override
  public void run(String... args) {
    game.addEntity(entityFactory.newRandomSlider().at(128, 128));
    game.addEntity(entityFactory.newRandomSlider().at(192, 192));
    game.addEntity(entityFactory.newRandomSlider().at(384, 384));
    game.addEntity(entityFactory.newPickaxe());
  }
}
