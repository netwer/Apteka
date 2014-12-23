package hello;

import hello.Helpers.Initializator;
import hello.Model.Order;
import hello.Model.Role;
import hello.Model.User;
import hello.Model.UserRole;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableAutoConfiguration
@ComponentScan
public class Application {

    public static void main(String[] args) {
        List<Role> roles = new ArrayList<Role>() ;
        roles.add(new Role(1,"admin"));
        roles.add(new Role(2,"patient"));

        List<Order> ordersOne = new ArrayList<Order>();
        List<String> drugsOne = new ArrayList<String>();
        drugsOne.add("Medical One");
        drugsOne.add("Medical Two");
        ordersOne.add(new Order(1,drugsOne, "done","12/23/2014","addres 1"));

        List<String> drugsTwo = new ArrayList<String>();
        drugsTwo.add("Medical One");
        drugsTwo.add("Medical Two");
        drugsTwo.add("Medical Three");
        ordersOne.add(new Order(2,drugsTwo, "inProcess","12/27/2014","addres 1"));

        List<Order> ordersTwo = new ArrayList<Order>();
        List<String> drugsThree = new ArrayList<String>();
        drugsThree.add("Medical One");
        drugsThree.add("Medical Two");
        ordersTwo.add(new Order(3,drugsThree, "inProcess","10/23/2014","addres 2"));

        List<String> drugsFour = new ArrayList<String>();
        drugsFour.add("Medical One");
        drugsFour.add("Medical Two");
        ordersTwo.add(new Order(4,drugsFour, "inProcess","10/23/2014","addres 2"));

        List<User> users = new ArrayList<User>();
        users.add(new User(1,"Vasya","12345",null));
        users.add(new User(2,"Petya","12345",ordersOne));
        users.add(new User(3,"Igor","12345",ordersTwo));

        List<UserRole> userRoles = new ArrayList<UserRole>();
        userRoles.add(new UserRole(1,1,1));
        userRoles.add(new UserRole(2,2,2));
        userRoles.add(new UserRole(3,3,2));

        List<Order> orders = new ArrayList<Order>();
        orders.addAll(ordersOne);
        orders.addAll(ordersTwo);

        Initializator initializator = new Initializator(users,roles,userRoles,orders);

        SpringApplication.run(Application.class, args);
    }

} 