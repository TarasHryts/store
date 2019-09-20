package mate.academy.shop.factory.generators;

public class UserIdGenerator {
    private static Long idGenerator = 0L;

    private UserIdGenerator() {
    }

    public static Long getIdGenerator() {
        return idGenerator++;
    }
}
