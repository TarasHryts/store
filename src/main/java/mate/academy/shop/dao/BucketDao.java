package mate.academy.shop.dao;

import java.util.List;
import java.util.Optional;
import mate.academy.shop.model.Bucket;
import mate.academy.shop.model.Item;

public interface BucketDao {
    Optional<Bucket> create(Bucket bucket);

    Optional<Bucket> get(Long bucketId);

    Optional<Bucket> update(Bucket bucket);

    Optional<Bucket> delete(Long id);

    void addBucketForUser(Long userId, Long bucketId);

    Optional<Bucket> getBucketByUser(Long userId);

    void addItemToBucket(Long itemId, Long bucketId);

    List<Item> getItemForBucket(Long bucketId);

    void deleteAllItemsFromBucket(Long bucketId);

    void deleteItemFromBucket(Long bucketId, Long itemId);
}
