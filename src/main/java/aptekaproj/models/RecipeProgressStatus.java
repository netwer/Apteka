package aptekaproj.models;

import javax.persistence.*;

/**
 * Created by Admin on 28.05.2015.
 */
@Entity(name = "models.RecipeProgressStatus")
@Table(name = "RecipeProgressStatus")
public class RecipeProgressStatus {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int Id;

    @Column(name = "name")
    private String Name;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
