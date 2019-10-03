package mate.academy.shop.dao.impl;

import java.util.List;
import java.util.NoSuchElementException;
import mate.academy.shop.anotation.Dao;
import mate.academy.shop.dao.ItemDao;
import mate.academy.shop.model.Item;
import mate.academy.shop.storage.Storage;
import org.apache.log4j.Logger;

@Dao
public class ItemDaoImpl implements ItemDao {
    private static final Logger logger = Logger.getLogger(ItemDaoImpl.class);

    @Override
    public Item create(Item item) {
        Storage.items.add(item);
        return item;
    }

    @Override
    public Item get(Long id) {
        return Storage.items
                .stream()
                .filter(i -> i.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Can't find item with id " + id));
    }

    @Override
    public Item update(Item item) {
        for (int i = 0; i < Storage.items.size(); i++) {
            if (Storage.items.get(i).getId().equals(item.getId())) {
                Storage.items.set(i, item);
                return item;
            }
        }
        logger.error("Can't find item with id " + item.getId());
        throw new NoSuchElementException("Can't find item with id " + item.getId());
    }

    @Override
    public void delete(Long id) {
        Item item = get(id);
        Storage.items.removeIf(x -> x.getId().equals(id));
    }

    @Override
    public List<Item> getAllItems() {
        return Storage.items;
    }

    @Override
    public void addItemToBucket(Long item_id, Long bucket_id) {

    }

    @Override
    public List<Item> getItemForBucket(Long bucketId) {
        return null;
    }

    @Override
    public void deleteAllItemsFromBucket(Long bucketId) {

    }

    @Override
    public void deleteItemFromBucket(Long bucketId, Long itemId) {

    }

    @Override
    public void addItemToOrder(Long item_id, Long order_id) {

    }
}
