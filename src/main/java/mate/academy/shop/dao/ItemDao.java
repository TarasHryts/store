package mate.academy.shop.dao;

import java.util.List;
import java.util.Optional;
import mate.academy.shop.model.Item;

public interface ItemDao {
    Optional<Item> create(Item item);

    Optional<Item> get(Long id);

    Optional<Item> update(Item item);

    void delete(Long id);

    List<Item> getAllItems();
}
