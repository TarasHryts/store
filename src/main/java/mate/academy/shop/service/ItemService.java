package mate.academy.shop.service;

import java.util.List;
import java.util.Optional;
import mate.academy.shop.model.Item;

public interface ItemService {
    Optional<Item> create(Item item);

    Optional<Item> get(Long id);

    Optional<Item> update(Item item);

    void delete(Long id);

    List<Item> getAll();

    List<Item> getItemForBucket(Long bucketId);
}
