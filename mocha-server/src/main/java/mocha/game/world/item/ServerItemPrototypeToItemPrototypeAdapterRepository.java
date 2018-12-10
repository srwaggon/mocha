package mocha.game.world.item;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import mocha.shared.Repository;

@org.springframework.stereotype.Repository
public class ServerItemPrototypeToItemPrototypeAdapterRepository implements Repository<ItemPrototype, Integer> {

  private ServerItemPrototypeRepository serverItemPrototypeRepository;

  @Inject
  public ServerItemPrototypeToItemPrototypeAdapterRepository(
      ServerItemPrototypeRepository serverItemPrototypeRepository
  ) {
    this.serverItemPrototypeRepository = serverItemPrototypeRepository;
  }

  @Override
  public List<ItemPrototype> findAll() {
    return Lists.newArrayList(serverItemPrototypeRepository.findAll());
  }

  @Override
  public ItemPrototype save(ItemPrototype element) {
    return serverItemPrototypeRepository.save(new ServerItemPrototype(element.getId(), element.getName(), element.getSpriteId(), element.getItemType(), element.getDescription()));
  }

  @Override
  public Optional<ItemPrototype> findById(Integer id) {
    Optional<ServerItemPrototype> byId = serverItemPrototypeRepository.findById(id);
    return byId.isPresent() ? Optional.of(byId.get()) : Optional.empty();
  }

  @Override
  public void delete(ItemPrototype element) {
    serverItemPrototypeRepository.findById(element.getId())
        .ifPresent(serverItemPrototypeRepository::delete);
  }
}
