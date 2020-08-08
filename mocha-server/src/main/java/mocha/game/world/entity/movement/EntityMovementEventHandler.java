package mocha.game.world.entity.movement;

import com.google.common.eventbus.Subscribe;

import org.springframework.stereotype.Component;

import java.util.Map;

import javax.inject.Inject;

import mocha.game.event.MochaEventHandler;
import mocha.game.world.entity.movement.event.EntityMovementEvent;
import mocha.net.packet.MochaConnection;

@Component
public class EntityMovementEventHandler implements MochaEventHandler<EntityMovementEvent> {

  private Map<Integer, MochaConnection> mochaConnectionsByPlayerId;

  @Inject
  public EntityMovementEventHandler(Map<Integer, MochaConnection> mochaConnectionsByPlayerId) {
    this.mochaConnectionsByPlayerId = mochaConnectionsByPlayerId;
  }

  @Subscribe
  public void handle(EntityMovementEvent entityMovementEvent) {
    mochaConnectionsByPlayerId.values().forEach(mochaConnection -> mochaConnection.sendMoveCommand(entityMovementEvent.getEntityMoveCommand()));
  }
}