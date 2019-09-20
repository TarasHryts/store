package mate.academy.shop.factory.generators;

public class ItemIdGenerator {
    private static Long idGenerator = 0L;

    private ItemIdGenerator() {
    }

    public static Long getIdGenerator() {
        return idGenerator++;
    }
}
