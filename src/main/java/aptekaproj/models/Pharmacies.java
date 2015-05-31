package aptekaproj.models;

import javax.persistence.*;

/**
 * Created by Admin on 28.05.2015.
 */
@Entity(name = "models.Pharmacies")
@Table(name = "Pharmacies")
public class Pharmacies {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int Id;

    @Column(name = "address")
    private String Address;

    @Column(name = "name")
    private String Name;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
