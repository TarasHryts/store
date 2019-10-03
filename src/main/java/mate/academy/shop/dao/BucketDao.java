package mate.academy.shop.dao;

import mate.academy.shop.model.Bucket;

public interface BucketDao {
    Bucket create(Bucket bucket);

    Bucket get(Long bucketId);

    Bucket update(Bucket bucket);

    Bucket delete(Long id);

    void addBucketForUser(Long user_id, Long bucket_id);

    Bucket getBucketByUser(Long userId);
}
