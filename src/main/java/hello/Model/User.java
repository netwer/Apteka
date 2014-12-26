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
    private String Login;
    private PatientRecords patientRecords;
    private String Name;
    private String Password;
    private List<Order> Orders;

    public User(long id,String login,String name,String pass,List<Order> orders,PatientRecords patientRecords)
    {
        this.Id = id;
        this.Login = login;
        this.patientRecords = patientRecords;
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

    public PatientRecords getPatientRecords() {
        return patientRecords;
    }

    public void setPatientRecords(PatientRecords patientRecords) {
        this.patientRecords = patientRecords;
    }

    public String getLogin() {
        return Login;
    }

    public List<Order> getOrders() {
        return Orders;
    }

    public void setOrders(List<Order> orders) {
        Orders = orders;
    }
}
