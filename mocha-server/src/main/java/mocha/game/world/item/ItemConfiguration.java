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
  public ItemPrototypeFactory itemPrototypeFactory() {
    return new ItemPrototypeFactory();
  }

  @Bean
  public ItemPrototypeService itemPrototypeService(Repository<ItemPrototype, Integer> itemPrototypeRepository, ItemPrototypeFactory itemPrototypeFactory) {
    return new ItemPrototypeService(itemPrototypeRepository, itemPrototypeFactory);
  }

  @Bean
  public Repository<Item, Integer> itemRepository() {
    return new InMemoryRepository<>();
  }

  @Bean
  public ItemFactory itemFactory(ItemPrototypeService itemPrototypeService) {
    return new ItemFactory(itemPrototypeService);
  }

  @Bean
  public ItemService itemService(Repository<Item, Integer> itemRepository, ItemFactory itemFactory) {
    return new ItemService(itemRepository, itemFactory);
  }
}
