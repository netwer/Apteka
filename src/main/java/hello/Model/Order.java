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
    private List<Drug> Drugs;
    private String Status;
    private String Date;
    private Apteka apteka;

    public Order (long id, List<Drug> drugs, String status, String date, Apteka apteka){
        this.Id = id;
        this.Drugs = drugs;
        this.Status = status;
        this.Date = date;
        this.apteka = apteka;
    }

    public long getId() {
        return Id;
    }

    public List<Drug> getDrugs() {
        return Drugs;
    }

    public String getStatus() {
        return Status;
    }

    public String getDate() {
        return Date;
    }

    public Apteka getApteka() {
        return apteka;
    }
}
