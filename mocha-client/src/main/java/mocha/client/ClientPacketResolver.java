package mocha.client;

import com.google.common.collect.Queues;
import com.google.common.eventbus.EventBus;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.inject.Inject;

import mocha.net.packet.Packet;
import mocha.net.packet.PacketHandler;
import mocha.net.packet.PacketResolver;
import mocha.shared.task.SleepyRunnable;

@Component
public class ClientPacketResolver implements PacketResolver, SleepyRunnable {

  private EventBus packetEventBus = new EventBus();
  private ConcurrentLinkedQueue<Packet> packets = Queues.newConcurrentLinkedQueue();

  private List<PacketHandler> packetHandlers;

  @Inject
  public ClientPacketResolver(List<PacketHandler> packetHandlers) {
    this.packetHandlers = packetHandlers;
  }

  private void setup() {
    packetHandlers.forEach(packetEventBus::register);
  }

  @Override
  public void run() {
    setup();
    packetEventBus.register(this);
    //noinspection InfiniteLoopStatement
    while (true) {
      if (!packets.isEmpty()) {
        Packet packet = packets.poll();
        packetEventBus.post(packet);
        nap();
      }
    }
  }

  @Override
  public void resolve(Packet packet) {
    packets.offer(packet);
  }

}
