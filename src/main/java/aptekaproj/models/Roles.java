package aptekaproj.models;

import java.io.Serializable;

import javax.persistence.*;


@Entity
@Table(name = "Roles")
public class Roles implements Serializable {

    @Id
    //@Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer Id;

    //@Column(name = "name")
    private String Name;

    public Roles(){}
    public Roles(Integer id){
        this.Id = id;
    }
    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
