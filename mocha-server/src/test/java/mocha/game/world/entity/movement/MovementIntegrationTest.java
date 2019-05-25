package mocha.game.world.entity.movement;

import org.junit.Test;

import javax.inject.Inject;

import mocha.MochaTest;
import mocha.game.world.Direction;
import mocha.game.world.Location;
import mocha.game.world.chunk.ChunkService;
import mocha.game.world.entity.Entity;
import mocha.net.exception.DisconnectedException;

import static mocha.game.world.Direction.EAST;
import static mocha.game.world.Direction.SOUTH;
import static mocha.game.world.chunk.tile.TileType.DIRT;
import static org.assertj.core.api.Assertions.assertThat;


public class MovementIntegrationTest extends MochaTest {

  @Inject
  private ChunkService chunkService;

  @Test
  public void entityMovesWhenToldToMove() throws DisconnectedException {
    registerAccount(player1Connection, player1AccountName);
    connectPlayer1ToGameServer();
    Entity entity = getEntityUpdate(player1Connection);
    ensureEntityCanMove(entity, EAST);
    moveEntity(entity, EAST);
    ensureEntityCanMove(entity, SOUTH);
    moveEntity(entity, SOUTH);
    Location locationOnDisconnect = entity.getLocation();
    assertThat(locationOnDisconnect).isNotEqualTo(new Location(0, 0));
  }

  private void ensureEntityCanMove(Entity entity, Direction direction) {
    chunkService.updateTileTypeAt(entity.getLocation().from(direction), DIRT);
  }

  @Test
  public void onlyTheRequestedEntityMoves_WhenRequested() throws DisconnectedException {
    registerAccount(player1Connection, player1AccountName);
    connectPlayer1ToGameServer();
    Entity entity1 = getEntityUpdate(player1Connection);
    int entity1StartingX = entity1.getLocation().getX();
    ensureEntityCanMove(entity1, EAST);
    moveEntity(entity1, EAST);

    registerAccount(player2Connection, player2AccountName);
    connectPlayer2ToGameServer();
    Entity entity2 = getEntityUpdate(player1Connection);
    Location entity2Location = entity2.getLocation();
    int entity2StartingX = entity2Location.getX();

    ensureEntityCanMove(entity1, EAST);
    moveEntity(entity1, EAST);

    assertThat(entity1.getLocation().getX()).isGreaterThan(0);
    assertThat(entity1.getLocation().getX()).isGreaterThan(entity1StartingX);
    assertThat(entity1.getLocation().getX()).isGreaterThan(entity2Location.getX());
    assertThat(entity2.getLocation().getX()).isZero();
    assertThat(entity2.getLocation().getX()).isEqualTo(entity2StartingX);
  }

}
