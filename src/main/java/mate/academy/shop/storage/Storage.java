package mate.academy.shop.storage;

import java.util.ArrayList;
import java.util.List;
import mate.academy.shop.model.Bucket;
import mate.academy.shop.model.Item;
import mate.academy.shop.model.Order;
import mate.academy.shop.model.User;

public class Storage {
    public static final List<Item> items = new ArrayList<>();
    public static final List<Bucket> buckets = new ArrayList<>();
    public static final List<Order> orders = new ArrayList<>();
    public static final List<User> users = new ArrayList<>();
}
