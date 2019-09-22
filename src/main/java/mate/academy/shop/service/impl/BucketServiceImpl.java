package mate.academy.shop.service.impl;

import java.util.List;
import mate.academy.shop.anotation.Inject;
import mate.academy.shop.anotation.Service;
import mate.academy.shop.dao.BucketDao;
import mate.academy.shop.dao.ItemDao;
import mate.academy.shop.model.Bucket;
import mate.academy.shop.model.Item;
import mate.academy.shop.service.BucketService;

@Service
public class BucketServiceImpl implements BucketService {
    @Inject
    private static BucketDao bucketDao;
    @Inject
    private static ItemDao itemDao;

    @Override
    public Bucket create(Bucket bucket) {
        return bucketDao.create(bucket);
    }

    @Override
    public Bucket get(Long id) {
        return bucketDao.get(id);
    }

    @Override
    public List<Item> getAllItems(Long bucketId) {
        return bucketDao.get(bucketId).getItems();
    }

    @Override
    public Bucket update(Bucket bucket) {
        return bucketDao.update(bucket);
    }

    @Override
    public Bucket addItem(Long bucketId, Long itemId) {
        Bucket bucket = bucketDao.get(bucketId);
        Item item = itemDao.get(itemId);
        bucket.getItems().add(item);
        return bucket;
    }

    @Override
    public Bucket delete(Long id) {
        return bucketDao.delete(id);
    }

    @Override
    public Bucket clear(Long bucketId) {
        Bucket bucket = bucketDao.get(bucketId);
        bucket.getItems().clear();
        return update(bucket);
    }

    @Override
    public Bucket deleteItem(Long bucketId, Long itemId) {
        Bucket bucket = bucketDao.get(bucketId);
        Item item = itemDao.get(itemId);
        bucket.getItems().remove(item);
        return update(bucket);
    }
}
