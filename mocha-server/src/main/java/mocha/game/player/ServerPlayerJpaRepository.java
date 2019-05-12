package mocha.game.player;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServerPlayerJpaRepository extends JpaRepository<ServerPlayer, Integer> {
}
