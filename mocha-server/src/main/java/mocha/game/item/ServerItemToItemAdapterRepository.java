package mocha.game.item;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import mocha.game.world.item.Item;
import mocha.game.world.item.ItemPrototype;
import mocha.shared.Repository;

@org.springframework.stereotype.Repository
public class ServerItemToItemAdapterRepository implements Repository<Item, Integer> {

  private ServerItemRepository serverItemRepository;

  @Inject
  public ServerItemToItemAdapterRepository(
      ServerItemRepository serverItemRepository
  ) {
    this.serverItemRepository = serverItemRepository;
  }

  @Override
  public List<Item> findAll() {
    return Lists.newArrayList(serverItemRepository.findAll());
  }

  @Override
  public Item save(Item element) {
    ItemPrototype itemPrototype = element.getItemPrototype();
    ServerItemPrototype serverItemPrototype = new ServerItemPrototype(itemPrototype.getId(), itemPrototype.getName(), itemPrototype.getSpriteId(), itemPrototype.getItemType(), itemPrototype.getDescription());
    return serverItemRepository.save(new ServerItem(element.getId(), serverItemPrototype, element.getData0(), element.getData1(), element.getData2()));
  }

  @Override
  public Optional<Item> findById(Integer id) {
    Optional<ServerItem> byId = serverItemRepository.findById(id);
    return byId.isPresent() ? Optional.of(byId.get()) : Optional.empty();
  }

  @Override
  public void delete(Item element) {
    serverItemRepository.findById(element.getId())
        .ifPresent(serverItemRepository::delete);
  }
}
