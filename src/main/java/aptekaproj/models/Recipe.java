package aptekaproj.models;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Admin on 25.04.2015.
 */
@Entity(name = "models.Recipes")
@Table(name = "Recipes")
public class Recipe {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "title")
    private String title;

    @Column(name = "pharmacy_id")
    private int pharmacyId;

    @Column(name = "recipe_progress_status_id")
    private int recipeProgressStatusId;

    @Column(name = "created_at")
    private Date createdAt;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public int getPharmacyId() {
        return pharmacyId;
    }

    public void setPharmacyId(int pharmacyId) {
        this.pharmacyId = pharmacyId;
    }

    public int getRecipeProgressStatusId() {
        return recipeProgressStatusId;
    }

    public void setRecipeProgressStatusId(int recipeProgressStatusId) {
        this.recipeProgressStatusId = recipeProgressStatusId;
    }
}
