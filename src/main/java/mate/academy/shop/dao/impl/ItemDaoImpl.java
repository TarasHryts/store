package mate.academy.shop.dao.impl;

import java.io.FileReader;
import java.util.List;
import java.util.NoSuchElementException;
import mate.academy.shop.anotation.Dao;
import mate.academy.shop.dao.ItemDao;
import mate.academy.shop.model.Item;
import mate.academy.shop.storage.Storage;
import org.apache.log4j.Logger;

@Dao
public class ItemDaoImpl implements ItemDao {
    static final Logger logger = Logger.getLogger(FileReader.class);

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
    public Item delete(Long id) {
        Item item = get(id);
        Storage.items.removeIf(x -> x.getId().equals(id));
        return item;
    }

    @Override
    public Item delete(Item item) {
        get(item.getId());
        Storage.items.removeIf(x -> x.getId().equals(item.getId()));
        return item;
    }

    @Override
    public List<Item> getAllItems() {
        return Storage.items;
    }
}
