package mate.academy.shop.factory.generators;

public class OrderIdGenerator {
    private static Long idGenerator = 0L;

    private OrderIdGenerator() {
    }

    public static Long getIdGenerator() {
        return idGenerator++;
    }
}
