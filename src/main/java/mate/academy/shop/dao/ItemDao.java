package mate.academy.shop.dao;

import java.util.List;
import mate.academy.shop.model.Item;

public interface ItemDao {
    Item create(Item item);

    Item get(Long id);

    Item update(Item item);

    Item delete(Long id);

    List<Item> getAllItems();
}
