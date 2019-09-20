package mate.academy.shop.dao.impl;

import mate.academy.shop.anotation.Dao;
import mate.academy.shop.dao.ItemDao;
import mate.academy.shop.model.Item;
import mate.academy.shop.storage.Storage;

import java.util.List;
import java.util.NoSuchElementException;

@Dao
public class ItemDaoImpl implements ItemDao {

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
