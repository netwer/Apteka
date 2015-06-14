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
    private int id;

    @Column(name = "recipe_id")
    private int recipeId;

    @Column(name = "drug_id")
    private int drugId;

    @Column(name = "done")
    private boolean done;

    @Column(name = "count")
    private int count;

    @Column(name = "progress_status_id")
    private Integer progressStatusId;

    public int getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    public int getDrugId() {
        return drugId;
    }

    public void setDrugId(int drugId) {
        this.drugId = drugId;
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
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProgressStatusId() {
        return progressStatusId;
    }

    public void setProgressStatusId(int progressStatusId) {
        this.progressStatusId = progressStatusId;
    }
}
