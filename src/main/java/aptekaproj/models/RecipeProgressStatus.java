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
    private int id;

    @Column(name = "name")
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
