package mate.academy.shop.dao;

import mate.academy.shop.model.Item;

import java.util.List;

public interface ItemDao {
    Item create(Item item);

    Item get(Long id);

    Item update(Item item);

    Item delete(Long id);

    Item delete(Item item);

    List<Item> getAllItems();
}
