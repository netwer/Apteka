package aptekaproj.models;

import javax.persistence.*;

/**
 * Created by Admin on 25.04.2015.
 */
@Entity(name = "models.RecipesHasDrugs")
@Table(name = "RecipesHasDrugs")
public class RecipesHasDrugs {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int Id;

    @Column(name = "recipe_id")
    private int recipe_id;

    @Column(name = "drug_id")
    private int drug_id;

    @Column(name = "progress_status_id")
    private int progress_status_id;

    @Column(name = "count")
    private int count;

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

    public int getProgress_status_id() {
        return progress_status_id;
    }

    public void setProgress_status_id(int progress_status_id) {
        this.progress_status_id = progress_status_id;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
