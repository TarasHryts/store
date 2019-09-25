package mate.academy.shop.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import mate.academy.shop.factory.generators.UserIdGenerator;

public class User {
    private final Long id;
    private String name;
    private String surname;
    private String login;
    private String password;
    private String token;
    private Bucket bucket;

    public User(String name, Bucket bucket) {
        this();
        this.name = name;
        this.bucket = bucket;
    }

    public User() {
        this.id = UserIdGenerator.getIdGenerator();
        this.orders = new ArrayList<Order>();
    }

    private Set<Role> roles = new HashSet<>();

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public void addRole(Role role) {
        roles.add(role);
    }

    public void deleteRole(Role role) {
        roles.remove(role);
    }

    public Bucket getBucket() {
        return bucket;
    }

    public void setBucket(Bucket bucket) {
        this.bucket = bucket;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

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

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Order> getOrders() {
        return orders;
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", name='" + name + '\'' + ", orders=" + orders + '}';
    }
}
