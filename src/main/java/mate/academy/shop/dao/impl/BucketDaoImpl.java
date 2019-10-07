package mate.academy.shop.dao.impl;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import mate.academy.shop.anotation.Dao;
import mate.academy.shop.dao.BucketDao;
import mate.academy.shop.model.Bucket;
import mate.academy.shop.model.Item;
import mate.academy.shop.storage.Storage;
import org.apache.log4j.Logger;

@Dao
public class BucketDaoImpl implements BucketDao {
    private static final Logger logger = Logger.getLogger(BucketDaoImpl.class);

    @Override
    public Optional<Bucket> create(Bucket bucket) {
        Storage.buckets.add(bucket);
        return Optional.of(bucket);
    }

    @Override
    public Optional<Bucket> get(Long bucketId) {
        return Optional.of(Storage.buckets
                .stream()
                .filter(b -> b.getId().equals(bucketId))
                .findFirst()
                .orElseThrow(() ->
                        new NoSuchElementException("Can't find bucket with id " + bucketId)));
    }

    @Override
    public Optional<Bucket> update(Bucket bucket) {
        for (int i = 0; i < Storage.buckets.size(); i++) {
            if (bucket.getId().equals(Storage.buckets.get(i).getId())) {
                Storage.buckets.set(i, bucket);
                return Optional.of(bucket);
            }
        }
        logger.error("Can't find element with id: " + bucket.getId());
        throw new NoSuchElementException("Can't find element with id: " + bucket.getId());
    }

    @Override
    public Optional<Bucket> delete(Long id) {
        Bucket bucket = get(id).get();
        Storage.buckets.removeIf(x -> id.equals(x.getId()));
        return Optional.of(bucket);
    }

    @Override
    public void addBucketForUser(Long userId, Long bucketId) {

    }

    @Override
    public Optional<Bucket> getBucketByUser(Long userId) {
        return null;
    }

    @Override
    public void addItemToBucket(Long itemId, Long bucketId) {

    }

    @Override
    public List<Item> getItemForBucket(Long bucketId) {
        return null;
    }

    @Override
    public void deleteAllItemsFromBucket(Long bucketId) {

    }

    @Override
    public void deleteItemFromBucket(Long bucketId, Long itemId) {

    }
}
