package aptekaproj.models;

import javax.persistence.*;

/**
 * Created by Admin on 25.04.2015.
 */
@Entity(name = "models.Drugs")
@Table(name = "Drugs")
public class Drugs {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int Id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
