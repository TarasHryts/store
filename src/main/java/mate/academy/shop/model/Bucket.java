package mate.academy.shop.model;

import java.util.List;

public class Bucket {
    private Long id;
    private Long userId;
    private List<Item> items;

    public Bucket() {
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getId() {
        return this.id;
    }

    public Long getUserId() {
        return userId;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}
