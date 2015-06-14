package aptekaproj.models;

import javax.persistence.*;

/**
 * Created by Admin on 28.05.2015.
 */
@Entity(name = "models.ConcreteDrugs")
@Table(name = "ConcreteDrugs")
public class ConcreteDrug {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "drug_id")
    private int drugId;

    @Column(name = "PharmacyStaff_id")
    private int pharmacyStaffId;

    @Column(name = "Recipe_id")
    private int recipeId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDrugId() {
        return drugId;
    }

    public void setDrugId(int drugId) {
        this.drugId = drugId;
    }

    public int getPharmacyStaffId() {
        return pharmacyStaffId;
    }

    public void setPharmacyStaffId(int pharmacyStaffId) {
        this.pharmacyStaffId = pharmacyStaffId;
    }

    public int getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }
}
