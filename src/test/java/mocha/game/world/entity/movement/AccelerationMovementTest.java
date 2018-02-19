package mocha.game.world.entity.movement;

import com.google.common.eventbus.EventBus;

import org.junit.Before;
import org.junit.Test;

import mocha.game.world.Location;
import mocha.game.world.entity.movement.collision.SimpleCollision;

import static org.assertj.core.api.Assertions.assertThat;


public class AccelerationMovementTest {

  private AccelerationMovement testObject;
  private long now = 0L;

  private EventBus eventBus = new EventBus();

  @Before
  public void setUp() {
    double accelerationRate = 2.0D;
    testObject = AccelerationMovement.builder()
        .collision(new SimpleCollision())
        .location(new Location())
        .eventBus(eventBus)
        .accelerationRate(accelerationRate)
        .build();
  }

  @Test
  public void theMovementIsStill_WhenNotMoving() {
    testObject.tick(now);

    Location actualLocation = testObject.getLocation();
    assertThat(actualLocation.getX()).isEqualTo(0.0D);
    assertThat(actualLocation.getY()).isEqualTo(0.0D);
  }

  @Test
  public void theMovementSpeedChangesByTheAccelerationRate_WhenMoving() {
    testObject.right();

    testObject.tick(now);

    Location actualLocation = testObject.getLocation();
    assertThat(actualLocation.getX()).isEqualTo(2.0D);
    assertThat(actualLocation.getY()).isEqualTo(0.0D);
  }

  @Test
  public void theMovementContinues_WithoutFurtherAcceleration() {
    testObject.right();
    testObject.tick(now);

    testObject.tick(now);

    Location actualLocation = testObject.getLocation();
    assertThat(actualLocation.getX()).isEqualTo(4.0D);
    assertThat(actualLocation.getY()).isEqualTo(0.0D);
  }

  @Test
  public void theMovementSpeedIncreases_TheLongerTheMovementAccelerates() {
    testObject.right();
    testObject.tick(now);
    testObject.right();

    testObject.tick(now);

    Location actualLocation = testObject.getLocation();
    assertThat(actualLocation.getX()).isEqualTo(6.0D);
    assertThat(actualLocation.getY()).isEqualTo(0.0D);
  }

  @Test
  public void theMovementOnlyAcceleratesOncePerTick() {
    testObject.right();
    testObject.right();
    testObject.right();

    testObject.tick(now);

    Location actualLocation = testObject.getLocation();
    assertThat(actualLocation.getX()).isEqualTo(2.0D);
    assertThat(actualLocation.getY()).isEqualTo(0.0D);
  }

  @Test
  public void theMovementCanGoLeftToo() {
    testObject.left();

    testObject.tick(now);

    Location actualLocation = testObject.getLocation();
    assertThat(actualLocation.getX()).isEqualTo(-2.0D);
    assertThat(actualLocation.getY()).isEqualTo(0.0D);
  }

  @Test
  public void theMovementComesToAStop_WhenCountered() {
    testObject.right();
    testObject.tick(now);
    testObject.left();
    testObject.tick(now);

    Location actualLocation = testObject.getLocation();
    assertThat(actualLocation.getX()).isEqualTo(2.0D);
    assertThat(actualLocation.getY()).isEqualTo(0.0D);
  }

  @Test
  public void theMovementCanContinueInTheOppositeDirection() {
    testObject.right();
    testObject.tick(now);
    testObject.left();
    testObject.tick(now);
    testObject.left();

    testObject.tick(now);
    testObject.tick(now);

    Location actualLocation = testObject.getLocation();
    assertThat(actualLocation.getX()).isEqualTo(-2.0D);
    assertThat(actualLocation.getY()).isEqualTo(0.0D);
  }

  @Test
  public void theMovementCanGoUp() {
    testObject.up();

    testObject.tick(now);

    Location actualLocation = testObject.getLocation();
    assertThat(actualLocation.getX()).isEqualTo(0.0D);
    assertThat(actualLocation.getY()).isEqualTo(-2.0D);
  }

  @Test
  public void theMovementCanGoDown() {
    testObject.down();

    testObject.tick(now);

    Location actualLocation = testObject.getLocation();
    assertThat(actualLocation.getX()).isEqualTo(0.0D);
    assertThat(actualLocation.getY()).isEqualTo(2.0D);
  }

  @Test
  public void theMovementCanGoInMultipleDirections() {
    testObject.down();
    testObject.right();

    testObject.tick(now);

    Location actualLocation = testObject.getLocation();
    assertThat(actualLocation.getX()).isEqualTo(2.0D);
    assertThat(actualLocation.getY()).isEqualTo(2.0D);
  }

  @Test
  public void theXMovementCapsOutAtAVelocity_WhenGivenALimit() {
    testObject.setMaxXVelocity(10.0D);
    double expectedX = 2.0 + 4.0 + 6.0 + 8.0 + 10.0 + 10.0 + 10.0;

    for (int i = 0; i < 7; i++) {
      testObject.right();
      testObject.tick(now);
    }

    Location actualLocation = testObject.getLocation();
    assertThat(actualLocation.getX()).isEqualTo(expectedX);
    assertThat(actualLocation.getY()).isEqualTo(0.0D);
  }

  @Test
  public void theXMovementLimitAppliesBothWays() {
    testObject.setMaxXVelocity(10.0D);
    double expectedX = -2.0 - 4.0 - 6.0 - 8.0 - 10.0 - 10.0 - 10.0;

    for (int i = 0; i < 7; i++) {
      testObject.left();
      testObject.tick(now);
    }

    Location actualLocation = testObject.getLocation();
    assertThat(actualLocation.getX()).isEqualTo(expectedX);
    assertThat(actualLocation.getY()).isEqualTo(0.0D);
  }

  @Test
  public void theYMovementCapsOutAtAVelocity_WhenGivenALimit() {
    testObject.setMaxYVelocity(8.0);
    double expectedY = 2.0 + 4.0 + 6.0 + 8.0 + 8.0 + 8.0;

    for (int i = 0; i < 6; i++) {
      testObject.down();
      testObject.tick(now);
    }

    Location actualLocation = testObject.getLocation();
    assertThat(actualLocation.getX()).isEqualTo(0.0D);
    assertThat(actualLocation.getY()).isEqualTo(expectedY);
  }

  @Test
  public void theYMovementLimitAppliesBothWays() {
    testObject.setMaxYVelocity(10.0D);
    double expectedY = -2.0 - 4.0 - 6.0 - 8.0 - 10.0 - 10.0 - 10.0;

    for (int i = 0; i < 7; i++) {
      testObject.up();
      testObject.tick(now);
    }

    Location actualLocation = testObject.getLocation();
    assertThat(actualLocation.getX()).isEqualTo(0.0D);
    assertThat(actualLocation.getY()).isEqualTo(expectedY);
  }

}