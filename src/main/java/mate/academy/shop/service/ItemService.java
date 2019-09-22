package mate.academy.shop.service;

import java.util.List;
import mate.academy.shop.model.Item;

public interface ItemService {
    Item create(Item item);

    Item get(Long id);

    Item update(Item item);

    Item delete(Long id);

    Item delete(Item item);

    List<Item> getAll();
}
