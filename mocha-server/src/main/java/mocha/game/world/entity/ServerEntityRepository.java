package mocha.game.world.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface ServerEntityRepository extends JpaRepository<ServerEntity, Integer> {
}
