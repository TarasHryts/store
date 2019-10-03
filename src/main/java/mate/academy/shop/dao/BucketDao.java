package mate.academy.shop.dao;

import mate.academy.shop.model.Bucket;

public interface BucketDao {
    Bucket create(Bucket bucket);

    Bucket get(Long bucketId);

    Bucket update(Bucket bucket);

    Bucket delete(Long id);

    void addBucketForUser(Long userId, Long bucketId);

    Bucket getBucketByUser(Long userId);
}
