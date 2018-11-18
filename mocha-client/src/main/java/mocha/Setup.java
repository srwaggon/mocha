package mocha;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

import mocha.game.Game;
import mocha.game.LocalPlayer;
import mocha.game.world.entity.Entity;
import mocha.game.world.entity.EntityFactory;

@Component
public class Setup implements CommandLineRunner {

  @Inject
  private Game game;

  @Inject
  private EntityFactory entityFactory;

  @Value("${mocha.client.online}")
  private boolean isOnline;

  @Override
  public void run(String... args) {
    if (!isOnline) {
      Entity playerEntity = entityFactory.newSlider();
      LocalPlayer player = new LocalPlayer(playerEntity);
      game.addPlayer(player);
    }
  }
}
