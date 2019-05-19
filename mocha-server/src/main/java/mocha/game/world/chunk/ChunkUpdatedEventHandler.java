package mocha.game.world.chunk;

import com.google.common.eventbus.Subscribe;

import org.springframework.stereotype.Component;

import java.util.Map;

import javax.inject.Inject;

import mocha.game.event.MochaEventHandler;
import mocha.game.world.chunk.event.ChunkUpdatedEvent;
import mocha.net.packet.MochaConnection;

@Component
public class ChunkUpdatedEventHandler implements MochaEventHandler<ChunkUpdatedEvent> {

  private Map<Integer, MochaConnection> mochaConnectionsByPlayerId;

  @Inject
  public ChunkUpdatedEventHandler(Map<Integer, MochaConnection> mochaConnectionsByPlayerId) {
    this.mochaConnectionsByPlayerId = mochaConnectionsByPlayerId;
  }

  @Subscribe
  public void handle(ChunkUpdatedEvent chunkUpdatedEvent) {
    Chunk chunk = chunkUpdatedEvent.getChunk();
    mochaConnectionsByPlayerId.values()
        .forEach(mochaConnection -> mochaConnection.sendChunkUpdate(chunk));
  }
}