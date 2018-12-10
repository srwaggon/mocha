package mocha.game.world.item;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import mocha.shared.InMemoryRepository;
import mocha.shared.Repository;

@Configuration
public class ItemConfiguration {

  @Bean
  public Repository<ItemPrototype, Integer> itemPrototypeRepository() {
    return new InMemoryRepository<>();
  }

  @Bean
  public ItemPrototypeService itemPrototypeService(Repository<ItemPrototype, Integer> itemPrototypeRepository) {
    return new ItemPrototypeService(itemPrototypeRepository);
  }

  @Bean
  public Repository<Item, Integer> itemRepository() {
    return new InMemoryRepository<>();
  }

  @Bean
  public ItemService itemService(Repository<Item, Integer> itemRepository, ItemPrototypeService itemPrototypeService) {
    return new ItemService(itemRepository, itemPrototypeService);
  }
}
