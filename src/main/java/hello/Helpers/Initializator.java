package hello.Helpers;

import hello.Model.Order;
import hello.Model.Role;
import hello.Model.User;
import hello.Model.UserRole;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Admin
 * Date: 23.12.14
 * Time: 22:57
 * To change this template use File | Settings | File Templates.
 */
public class Initializator {
    private static List<User> users;
    private static List<Role> roles;
    private static List<UserRole> userRoles;
    private static List<Order> orders;

    public Initializator (List<User> users, List<Role> roles, List<UserRole> userRoles,List<Order> orders) {
        this.users = users;
        this.roles = roles;
        this.userRoles = userRoles;
        this.orders = orders;
    }

    public static List<User> getUsers() {
        return users;
    }

    public static List<Role> getRoles() {
        return roles;
    }

    public static List<UserRole> getUserRoles() {
        return userRoles;
    }

    public static List<Order> getOrders() {
        return orders;
    }
}
