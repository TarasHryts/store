package mate.academy.shop.service;

import mate.academy.shop.model.Bucket;
import mate.academy.shop.model.Item;

import java.util.List;

public interface BucketService {
    Bucket create(Bucket bucket);

    Bucket get(Long id);

    List<Item> getAllItems(Long bucketId);

    Bucket update(Bucket bucket);

    Bucket addItem(Long bucketId, Long itemId);

    Bucket delete(Long id);

    Bucket clear(Long bucketId);

    Bucket deleteItem(Long bucketId, Long itemId);
}