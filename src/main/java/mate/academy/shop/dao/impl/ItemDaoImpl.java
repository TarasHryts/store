package mate.academy.shop.dao.impl;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import mate.academy.shop.anotation.Dao;
import mate.academy.shop.dao.ItemDao;
import mate.academy.shop.model.Item;
import mate.academy.shop.storage.Storage;
import org.apache.log4j.Logger;

@Dao
public class ItemDaoImpl implements ItemDao {
    private static final Logger logger = Logger.getLogger(ItemDaoImpl.class);

    @Override
    public Optional<Item> create(Item item) {
        Storage.items.add(item);
        return Optional.of(item);
    }

    @Override
    public Optional<Item> get(Long id) {
        return Optional.of(Storage.items
                .stream()
                .filter(i -> i.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Can't find item with id " + id)));
    }

    @Override
    public Optional<Item> update(Item item) {
        for (int i = 0; i < Storage.items.size(); i++) {
            if (Storage.items.get(i).getId().equals(item.getId())) {
                Storage.items.set(i, item);
                return Optional.of(item);
            }
        }
        logger.error("Can't find item with id " + item.getId());
        throw new NoSuchElementException("Can't find item with id " + item.getId());
    }

    @Override
    public void delete(Long id) {
        Item item = get(id).get();
        Storage.items.removeIf(x -> x.getId().equals(id));
    }

    @Override
    public List<Item> getAllItems() {
        return Storage.items;
    }
}
