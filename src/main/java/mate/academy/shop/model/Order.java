package mate.academy.shop.model;

import java.util.List;

public class Order {
    private Long id;
    private Long userId;
    private List<Item> items;

    public Order() {
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

    @Override
    public String toString() {
        return "Order{" + "id=" + id + ", userId=" + userId + ", items=" + items + '}';
    }
}
