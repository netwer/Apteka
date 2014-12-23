package hello.Model;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Admin
 * Date: 23.12.14
 * Time: 21:59
 * To change this template use File | Settings | File Templates.
 */
public class User {
    private long Id;
    private String Name;
    private String Password;
    private List<Order> Orders;

    public User(long id,String name,String pass,List<Order> orders)
    {
        this.Id = id;
        this.Name = name;
        this.Password = pass;
        this.Orders = orders;
    }

    public long getId() {
        return Id;
    }

    public String getName() {
        return Name;
    }

    public String getPassword() {
        return Password;
    }

    public List<Order> getOrders() {
        return Orders;
    }
}
