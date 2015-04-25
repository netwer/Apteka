package aptekaproj.models;

import javax.persistence.*;

/**
 * Created by Admin on 25.04.2015.
 */
@Entity(name = "models.DrugProgressStatuses")
@Table(name = "DrugProgressStatuses")
public class DrugProgressStatuses {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int Id;

    @Column(name = "title")
    private String title;

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
}
