package mocha.game.world.entity;

import com.google.common.eventbus.Subscribe;

import org.springframework.stereotype.Component;

import java.util.Map;

import javax.inject.Inject;

import mocha.game.event.MochaEventHandler;
import mocha.game.world.entity.event.EntityRemovedEvent;
import mocha.net.packet.MochaConnection;

@Component
public class EntityRemovedEventHandler implements MochaEventHandler<EntityRemovedEvent> {

  private Map<Integer, MochaConnection> mochaConnectionsByPlayerId;

  @Inject
  public EntityRemovedEventHandler(Map<Integer, MochaConnection> mochaConnectionsByPlayerId) {
    this.mochaConnectionsByPlayerId = mochaConnectionsByPlayerId;
  }

  @Subscribe
  public void handle(EntityRemovedEvent entityRemovedEvent) {
    mochaConnectionsByPlayerId.values().forEach(mochaConnection ->
        mochaConnection.sendEntityRemoved(entityRemovedEvent.getEntity()));
  }
}