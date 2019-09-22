package mate.academy.shop.dao.impl;

import java.io.FileReader;
import java.util.NoSuchElementException;
import mate.academy.shop.anotation.Dao;
import mate.academy.shop.dao.BucketDao;
import mate.academy.shop.model.Bucket;
import mate.academy.shop.storage.Storage;
import org.apache.log4j.Logger;

@Dao
public class BucketDaoImpl implements BucketDao {
    static final Logger logger = Logger.getLogger(FileReader.class);

    @Override
    public Bucket create(Bucket bucket) {
        Storage.buckets.add(bucket);
        return bucket;
    }

    @Override
    public Bucket get(Long bucketId) {
        return Storage.buckets
                .stream()
                .filter(b -> b.getId().equals(bucketId))
                .findFirst()
                .orElseThrow(() ->
                        new NoSuchElementException("Can't find bucket with id " + bucketId));
    }

    @Override
    public Bucket update(Bucket bucket) {
        for (int i = 0; i < Storage.buckets.size(); i++) {
            if (bucket.getId().equals(Storage.buckets.get(i).getId())) {
                Storage.buckets.set(i, bucket);
                return bucket;
            }
        }
        logger.error("Can't find element with id: " + bucket.getId());
        throw new NoSuchElementException("Can't find element with id: " + bucket.getId());
    }

    @Override
    public Bucket delete(Long id) {
        Bucket bucket = get(id);
        Storage.buckets.removeIf(x -> id.equals(x.getId()));
        return bucket;
    }
}
