package mocha.game;

import org.springframework.stereotype.Service;

import javax.inject.Inject;

import mocha.game.world.entity.Entity;
import mocha.shared.Repository;

@Service
public class PlayerService {

  private Repository<Player, Integer> playerRepository;

  @Inject
  public PlayerService(
      Repository<Player, Integer> playerRepository
  ) {
    this.playerRepository = playerRepository;
  }

  public Entity getEntityForPlayer() {
    return playerRepository.findAll().get(0).getEntity();
  }

}
