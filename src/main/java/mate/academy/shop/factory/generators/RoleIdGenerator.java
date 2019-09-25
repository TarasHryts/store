package mate.academy.shop.factory.generators;

public class RoleIdGenerator {
    private static Long idGenerator = 0L;

    private RoleIdGenerator() {
    }

    public static Long getIdGenerator() {
        return idGenerator++;
    }

}
