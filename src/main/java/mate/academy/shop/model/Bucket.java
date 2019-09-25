package mate.academy.shop.model;

import java.util.ArrayList;
import java.util.List;
import mate.academy.shop.factory.generators.BucketIdGenerator;

public class Bucket {
    private Long id;
    private List<Item> items;
    private Long userId;

    public Bucket(Long userId) {
        this.id = BucketIdGenerator.getIdGenerator();
        items = new ArrayList<Item>();
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
