package mate.academy.shop.service;

import java.util.List;
import java.util.Optional;
import mate.academy.shop.model.Bucket;
import mate.academy.shop.model.Item;

public interface BucketService {
    Optional<Bucket> create(Bucket bucket);

    Optional<Bucket> get(Long id);

    List<Item> getAllItems(Long bucketId);

    Optional<Bucket> update(Bucket bucket);

    Optional<Bucket> addItem(Long bucketId, Long itemId);

    Optional<Bucket> delete(Long id);

    Optional<Bucket> clear(Long bucketId);

    Optional<Bucket> deleteItem(Long bucketId, Long itemId);

    Optional<Bucket> getBucketByUser(Long userId);
}
