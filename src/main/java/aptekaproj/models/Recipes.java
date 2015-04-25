package aptekaproj.models;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Admin on 25.04.2015.
 */
@Entity(name = "models.Recipes")
@Table(name = "Recipes")
public class Recipes {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int Id;

    @Column(name = "title")
    private String title;

    @Column(name = "created_at")
    private Date created_at;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }
}
