package mocha.game.world.chunk;

import com.google.common.eventbus.Subscribe;

import mocha.game.event.MochaEventBus;
import mocha.net.event.ConnectedEvent;
import mocha.net.packet.MochaConnection;
import mocha.shared.Repository;

public class NetworkChunkService extends ChunkService {

  private final Repository<Chunk, Integer> chunkRepository;
  private MochaConnection mochaConnection;

  public NetworkChunkService(
      MochaEventBus mochaEventBus,
      ChunkFactory chunkFactory,
      Repository<Chunk, Integer> chunkRepository
  ) {
    super(mochaEventBus, chunkFactory, chunkRepository);
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
