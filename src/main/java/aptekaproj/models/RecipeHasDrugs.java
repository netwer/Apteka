package aptekaproj.models;

import javax.persistence.*;

/**
 * Created by Admin on 25.04.2015.
 */
@Entity(name = "models.RecipesHasDrugs")
@Table(name = "RecipesHasDrugs")
public class RecipeHasDrugs {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int Id;

    @Column(name = "recipe_id")
    private int recipe_id;

    @Column(name = "drug_id")
    private int drug_id;

    @Column(name = "done")
    private boolean done;

    @Column(name = "count")
    private int count;

    @Column(name = "progress_status_id")
    private Integer progress_status_id;


    public int getRecipe_id() {
        return recipe_id;
    }

    public void setRecipe_id(int recipe_id) {
        this.recipe_id = recipe_id;
    }

    public int getDrug_id() {
        return drug_id;
    }

    public void setDrug_id(int drug_id) {
        this.drug_id = drug_id;
    }

    public boolean getDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getProgress_status_id() {
        return progress_status_id;
    }

    public void setProgress_status_id(int progress_status_id) {
        this.progress_status_id = progress_status_id;
    }
}
