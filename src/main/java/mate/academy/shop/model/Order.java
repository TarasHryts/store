package mate.academy.shop.model;

import mate.academy.shop.factory.generators.OrderIdGenerator;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private final Long id;
    private final Long userId;
    private final List<Item> items;

    public Order(Long userId, List<Item> itemList) {
        this.id = OrderIdGenerator.getIdGenerator();
        this.userId = userId;
        this.items = new ArrayList<Item>(itemList);
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", userId=" + userId +
                ", items=" + items +
                '}';
    }

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public List<Item> getItems() {
        return items;
    }
}
