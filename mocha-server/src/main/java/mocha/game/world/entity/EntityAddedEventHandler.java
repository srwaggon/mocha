package mocha.game.world.entity;

import com.google.common.eventbus.Subscribe;

import org.springframework.stereotype.Component;

import java.util.Map;

import javax.inject.Inject;

import mocha.game.event.MochaEventHandler;
import mocha.game.world.entity.event.EntityAddedEvent;
import mocha.net.packet.MochaConnection;

@Component
public class EntityAddedEventHandler implements MochaEventHandler<EntityAddedEvent> {

  private Map<Integer, MochaConnection> mochaConnectionsByPlayerId;

  @Inject
  public EntityAddedEventHandler(Map<Integer, MochaConnection> mochaConnectionsByPlayerId) {
    this.mochaConnectionsByPlayerId = mochaConnectionsByPlayerId;
  }

  @Subscribe
  public void handle(EntityAddedEvent entityAddedEvent) {
    mochaConnectionsByPlayerId.values().forEach(mochaConnection ->
        mochaConnection.sendEntityUpdate(entityAddedEvent.getEntity()));
  }
}