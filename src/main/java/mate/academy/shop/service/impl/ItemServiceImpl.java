package mate.academy.shop.service.impl;

import java.util.List;
import java.util.Optional;
import mate.academy.shop.anotation.Inject;
import mate.academy.shop.anotation.Service;
import mate.academy.shop.dao.BucketDao;
import mate.academy.shop.dao.ItemDao;
import mate.academy.shop.model.Item;
import mate.academy.shop.service.ItemService;

@Service
public class ItemServiceImpl implements ItemService {
    @Inject
    private static ItemDao itemDao;
    @Inject
    private static BucketDao bucketDao;

    @Override
    public Optional<Item> create(Item item) {
        return itemDao.create(item);
    }

    @Override
    public Optional<Item> get(Long id) {
        return itemDao.get(id);
    }

    @Override
    public Optional<Item> update(Item item) {
        return itemDao.update(item);
    }

    @Override
    public void delete(Long id) {
        itemDao.delete(id);
    }

    @Override
    public List<Item> getAll() {

        return itemDao.getAllItems();
    }

    @Override
    public List<Item> getItemForBucket(Long bucketId) {
        return bucketDao.getItemsFromBucket(bucketId);
    }
}
