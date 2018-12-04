package mocha.game.world.chunk;

import com.google.common.eventbus.Subscribe;

import org.springframework.stereotype.Component;

import mocha.game.event.MochaEventBus;
import mocha.net.event.ConnectedEvent;
import mocha.net.packet.MochaConnection;
import mocha.shared.Repository;

@Component
public class NetworkChunkService extends ChunkService {

  private final Repository<Chunk, Integer> chunkRepository;
  private MochaConnection mochaConnection;

  public NetworkChunkService(
      ChunkFactory chunkFactory,
      Repository<Chunk, Integer> chunkRepository,
      MochaEventBus mochaEventBus
  ) {
    super(chunkFactory, chunkRepository);
    this.chunkRepository = chunkRepository;
    mochaEventBus.register(this);
  }

  @Subscribe
  public void handle(ConnectedEvent connectedEvent) {
    this.mochaConnection = connectedEvent.getMochaConnection();
  }

  @Override
  public Chunk getOrCreateChunkById(int chunkId) {
    if (!chunkRepository.findById(chunkId).isPresent()) {
      mochaConnection.requestChunkById(chunkId);
    }
    return super.getOrCreateChunkById(chunkId);
  }
}
