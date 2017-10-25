package mocha.game.world.entity.movement;

import org.junit.Before;
import org.junit.Test;

import mocha.game.world.Location;

import static org.assertj.core.api.Assertions.assertThat;


public class AccelerationMovementTest {

  private AccelerationMovement testObject;
  long now = 0L;

  @Before
  public void setUp() throws Exception {
    double accelerationRate = 2.0D;
    testObject = AccelerationMovement.builder()
        .location(new Location())
        .accelerationRate(accelerationRate)
        .build();
  }

  @Test
  public void theMovementIsStill_WhenNotMoving() throws Exception {
    testObject.tick(now);

    Location actualLocation = testObject.getLocation();
    assertThat(actualLocation.getX()).isEqualTo(0.0D);
    assertThat(actualLocation.getY()).isEqualTo(0.0D);
  }

  @Test
  public void theMovementSpeedChangesByTheAccelerationRate_WhenMoving() throws Exception {
    testObject.right();

    testObject.tick(now);

    Location actualLocation = testObject.getLocation();
    assertThat(actualLocation.getX()).isEqualTo(2.0D);
    assertThat(actualLocation.getY()).isEqualTo(0.0D);
  }

  @Test
  public void theMovementContinues_WithoutFurtherAcceleration() throws Exception {
    testObject.right();
    testObject.tick(now);

    testObject.tick(now);

    Location actualLocation = testObject.getLocation();
    assertThat(actualLocation.getX()).isEqualTo(4.0D);
    assertThat(actualLocation.getY()).isEqualTo(0.0D);
  }

  @Test
  public void theMovementSpeedIncreases_TheLongerTheMovementAccelerates() throws Exception {
    testObject.right();
    testObject.tick(now);
    testObject.right();

    testObject.tick(now);

    Location actualLocation = testObject.getLocation();
    assertThat(actualLocation.getX()).isEqualTo(6.0D);
    assertThat(actualLocation.getY()).isEqualTo(0.0D);
  }

  @Test
  public void theMovementOnlyAcceleratesOncePerTick() throws Exception {
    testObject.right();
    testObject.right();
    testObject.right();

    testObject.tick(now);

    Location actualLocation = testObject.getLocation();
    assertThat(actualLocation.getX()).isEqualTo(2.0D);
    assertThat(actualLocation.getY()).isEqualTo(0.0D);
  }

  @Test
  public void theMovementCanGoLeftToo() throws Exception {
    testObject.left();

    testObject.tick(now);

    Location actualLocation = testObject.getLocation();
    assertThat(actualLocation.getX()).isEqualTo(-2.0D);
    assertThat(actualLocation.getY()).isEqualTo(0.0D);
  }

  @Test
  public void theMovementComesToAStop_WhenCountered() throws Exception {
    testObject.right();
    testObject.tick(now);
    testObject.left();
    testObject.tick(now);

    Location actualLocation = testObject.getLocation();
    assertThat(actualLocation.getX()).isEqualTo(2.0D);
    assertThat(actualLocation.getY()).isEqualTo(0.0D);
  }

  @Test
  public void theMovementCanContinueInTheOppositeDirection() throws Exception {
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
  public void theMovementCanGoUp() throws Exception {
    testObject.up();

    testObject.tick(now);

    Location actualLocation = testObject.getLocation();
    assertThat(actualLocation.getX()).isEqualTo(0.0D);
    assertThat(actualLocation.getY()).isEqualTo(-2.0D);
  }

  @Test
  public void theMovementCanGoDown() throws Exception {
    testObject.down();

    testObject.tick(now);

    Location actualLocation = testObject.getLocation();
    assertThat(actualLocation.getX()).isEqualTo(0.0D);
    assertThat(actualLocation.getY()).isEqualTo(2.0D);
  }

  @Test
  public void theMovementCanGoInMultipleDirections() throws Exception {
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
  public void theXMovementLimitAppliesBothWays() throws Exception {
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
  public void theYMovementLimitAppliesBothWays() throws Exception {
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