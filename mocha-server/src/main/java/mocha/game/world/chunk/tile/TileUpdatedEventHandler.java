package mocha.game.world.chunk.tile;

import com.google.common.eventbus.Subscribe;

import org.springframework.stereotype.Component;

import java.util.Map;

import javax.inject.Inject;

import mocha.game.event.MochaEventHandler;
import mocha.game.world.Location;
import mocha.game.world.chunk.tile.event.TileUpdatedEvent;
import mocha.net.packet.MochaConnection;

@Component
public class TileUpdatedEventHandler implements MochaEventHandler<TileUpdatedEvent> {

  private Map<Integer, MochaConnection> mochaConnectionsByPlayerId;

  @Inject
  public TileUpdatedEventHandler(Map<Integer, MochaConnection> mochaConnectionsByPlayerId) {
    this.mochaConnectionsByPlayerId = mochaConnectionsByPlayerId;
  }

  @Subscribe
  public void handle(TileUpdatedEvent tileUpdatedEvent) {
    int x = tileUpdatedEvent.getX();
    int y = tileUpdatedEvent.getY();
    TileType tileType = tileUpdatedEvent.getTileType();
    Location location = new Location(x, y);
    mochaConnectionsByPlayerId.values()
        .forEach(mochaConnection -> mochaConnection.sendTileUpdate(location, tileType));
  }
}