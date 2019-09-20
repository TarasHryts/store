package mate.academy.shop.model;

import mate.academy.shop.factory.generators.UserIdGenerator;

import java.util.ArrayList;
import java.util.List;

public class User {
    private final Long id;
    private String name;
    private String surname;
    private String login;
    private String password;

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private List<Order> orders;

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", name='" + name + '\'' + ", orders=" + orders + '}';
    }

    public User(String name) {
        this.id = UserIdGenerator.getIdGenerator();
        this.name = name;
        this.orders = new ArrayList<Order>();
    }

    public User() {
        this.id = UserIdGenerator.getIdGenerator();
        this.orders = new ArrayList<Order>();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Order> getOrders() {
        return orders;
    }
}
