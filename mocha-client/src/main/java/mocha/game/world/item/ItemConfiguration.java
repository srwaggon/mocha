package mocha.game.world.item;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import mocha.shared.Repository;

@Configuration
public class ItemConfiguration {

  @Bean
  public ItemService itemService(Repository<Item, Integer> itemRepository, ItemPrototypeService itemPrototypeService) {
    return new ItemService(itemRepository, itemPrototypeService);
  }

  @Bean
  public ItemPrototypeFactory itemPrototypeFactory() {
    return new ItemPrototypeFactory();
  }

  @Bean
  public ItemPrototypeService itemPrototypeService(Repository<ItemPrototype, Integer> itemPrototypeRepository, ItemPrototypeFactory itemPrototypeFactory) {
    return new ItemPrototypeService(itemPrototypeRepository, itemPrototypeFactory);
  }
}
