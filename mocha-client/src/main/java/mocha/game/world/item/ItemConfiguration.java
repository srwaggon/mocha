package mocha.game.world.item;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import mocha.game.world.item.itemprototype.ItemPrototype;
import mocha.game.world.item.itemprototype.ItemPrototypeFactory;
import mocha.game.world.item.itemprototype.ItemPrototypeService;
import mocha.shared.InMemoryRepository;
import mocha.shared.Repository;

@Configuration
public class ItemConfiguration {

  @Bean
  public ItemPrototypeFactory itemPrototypeFactory() {
    return new ItemPrototypeFactory();
  }

  @Bean
  public Repository<ItemPrototype, Integer> itemPrototypeRepository() {
    return new InMemoryRepository<>();
  }

  @Bean
  public ItemPrototypeService itemPrototypeService(Repository<ItemPrototype, Integer> itemPrototypeRepository, ItemPrototypeFactory itemPrototypeFactory) {
    return new ItemPrototypeService(itemPrototypeRepository, itemPrototypeFactory);
  }

  @Bean
  public ItemFactory itemFactory(ItemPrototypeService itemPrototypeService) {
    return new ItemFactory(itemPrototypeService);
  }

  @Bean
  public Repository<Item, Integer> itemRepository() {
    return new InMemoryRepository<>();
  }

  @Bean
  public ItemService itemService(Repository<Item, Integer> itemRepository, ItemFactory itemFactory) {
    return new ItemService(itemRepository, itemFactory);
  }
}
