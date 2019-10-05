package mate.academy.shop.dao;

import java.util.List;
import mate.academy.shop.model.Bucket;
import mate.academy.shop.model.Item;

public interface BucketDao {
    Bucket create(Bucket bucket);

    Bucket get(Long bucketId);

    Bucket update(Bucket bucket);

    Bucket delete(Long id);

    void addBucketForUser(Long userId, Long bucketId);

    Bucket getBucketByUser(Long userId);

    void addItemToBucket(Long itemId, Long bucketId);

    List<Item> getItemForBucket(Long bucketId);

    void deleteAllItemsFromBucket(Long bucketId);

    void deleteItemFromBucket(Long bucketId, Long itemId);
}
