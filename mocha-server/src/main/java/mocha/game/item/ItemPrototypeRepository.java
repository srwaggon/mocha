package mocha.game.item;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemPrototypeRepository extends JpaRepository<ItemPrototype, Long> {
}
