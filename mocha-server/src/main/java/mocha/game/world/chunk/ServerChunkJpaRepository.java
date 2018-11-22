package mocha.game.world.chunk;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServerChunkJpaRepository extends JpaRepository<ServerChunk, Integer> {
}
