package mocha.game.world.entity;

import org.springframework.stereotype.Component;

import javax.inject.Inject;

import mocha.game.InputHandler;

@Component
public class EntityInput {

  @Inject
  private InputHandler input;
}
