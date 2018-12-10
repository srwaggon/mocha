package mocha.game.world.item;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServerItemRepository extends JpaRepository<ServerItem, Integer> {
}
