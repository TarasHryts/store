package mate.academy.shop.factory.generators;

public class BucketIdGenerator {
    private static Long idGenerator = 0L;

    private BucketIdGenerator() {

    }

    public static Long getIdGenerator() {
        return idGenerator++;
    }
}
