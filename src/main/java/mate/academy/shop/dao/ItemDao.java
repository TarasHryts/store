package mate.academy.shop.dao;

import java.util.List;
import mate.academy.shop.model.Item;

public interface ItemDao {
    Item create(Item item);

    Item get(Long id);

    Item update(Item item);

    void delete(Long id);

    List<Item> getAllItems();

    void addItemToBucket(Long item_id, Long bucket_id);

    List<Item> getItemForBucket(Long bucketId);

    void deleteAllItemsFromBucket(Long bucketId);

    void deleteItemFromBucket(Long bucketId, Long itemId);

    void addItemToOrder(Long item_id, Long order_id);
}
