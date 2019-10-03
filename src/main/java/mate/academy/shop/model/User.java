package mate.academy.shop.model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class User {
    private Long id;
    private String name;
    private String surname;
    private String login;
    private String password;
    private String token;
    private Bucket bucket;
    private Set<Role> roles = new HashSet<>();


    public User(String name, Bucket bucket) {
        this.name = name;
        this.bucket = bucket;
    }

    public User() {
    }

    public User(Long id) {
        this.id = id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
