package mocha.game.world.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServerEntityJpaRepository extends JpaRepository<ServerEntity, Integer>, mocha.shared.Repository<ServerEntity, Integer> {
}
