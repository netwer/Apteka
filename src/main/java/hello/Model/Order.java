package hello.Model;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Admin
 * Date: 23.12.14
 * Time: 23:13
 * To change this template use File | Settings | File Templates.
 */
public class Order {
    private long Id;
    private List<String> Drugs;
    private String Status;
    private String Date;
    private String Address;

    public Order (long id, List<String> drugs, String status, String date, String address){
        this.Id = id;
        this.Drugs = drugs;
        this.Status = status;
        this.Date = date;
        this.Address = address;
    }

    public long getId() {
        return Id;
    }

    public List<String> getDrugs() {
        return Drugs;
    }

    public String getStatus() {
        return Status;
    }

    public String getDate() {
        return Date;
    }

    public String getAddress() {
        return Address;
    }
}
