package mate.academy.shop.service.impl;

import mate.academy.shop.anotation.Inject;
import mate.academy.shop.anotation.Service;
import mate.academy.shop.dao.ItemDao;
import mate.academy.shop.model.Item;
import mate.academy.shop.service.ItemService;

import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {
    @Inject
    private static ItemDao itemDao;

    @Override
    public Item create(Item item) {
        return itemDao.create(item);
    }

    @Override
    public Item get(Long id) {
        return itemDao.get(id);
    }

    @Override
    public Item update(Item item) {
        return itemDao.update(item);
    }

    @Override
    public Item delete(Long id) {
        return itemDao.delete(id);
    }

    @Override
    public Item delete(Item item) {
        return itemDao.delete(item);
    }

    @Override
    public List<Item> getAll() {

        return itemDao.getAllItems();
    }
}