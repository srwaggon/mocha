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
  public void onlyTheRequestedEntityMoves_WhenRequestingTheFirstEntityToMove() throws DisconnectedException {
    registerAccount(player1Connection, player1AccountName);
    connectPlayer1ToGameServer();
    Entity entity1 = getEntityUpdate(player1Connection);
    Location entity1Location = entity1.getLocation();
    int entity1StartingX = entity1Location.getX();
    ensureEntityCanMove(entity1, EAST);
    moveEntity(entity1, EAST);

    registerAccount(player2Connection, player2AccountName);
    connectPlayer2ToGameServer();
    Entity entity2 = getEntityUpdate(player1Connection);
    Location entity2Location = entity2.getLocation();
    int entity2StartingX = entity2Location.getX();

    ensureEntityCanMove(entity1, EAST);
    moveEntity(entity1, EAST);

    // entity 1 has moved east
    assertThat(entity1Location.getX()).isGreaterThan(0);
    assertThat(entity1Location.getX()).isGreaterThan(entity1StartingX);
    // entity 2 has not moved east
    assertThat(entity1Location.getX()).isGreaterThan(entity2Location.getX());
    assertThat(entity2Location.getX()).isZero();
    assertThat(entity2Location.getX()).isEqualTo(entity2StartingX);
  }

  @Test
  public void onlyTheRequestedEntityMoves_WhenTheSecondEntityIsAskedToMove() throws DisconnectedException {
    registerAccount(player1Connection, player1AccountName);
    connectPlayer1ToGameServer();
    Entity entity1 = getEntityUpdate(player1Connection);
    Location entity1Location = entity1.getLocation();
    ensureEntityCanMove(entity1, EAST);
    moveEntity(entity1, EAST);

    registerAccount(player2Connection, player2AccountName);
    connectPlayer2ToGameServer();
    Entity entity2 = getEntityUpdate(player1Connection);
    Location entity2Location = entity2.getLocation();
    int entity2StartingY = entity2Location.getY();

    ensureEntityCanMove(entity2, SOUTH);
    moveEntity(entity2, SOUTH);

    // entity 2 has moved south
    assertThat(entity2Location.getY()).isGreaterThan(0);
    assertThat(entity2Location.getY()).isGreaterThan(entity2StartingY);
    // entity 1 has not moved south
    assertThat(entity1Location.getY()).isZero();
    assertThat(entity2Location.getY()).isGreaterThan(entity1Location.getY());
  }
}
