package aptekaproj.models;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Admin on 28.05.2015.
 */
@Entity(name = "models.ConcreteIngredients")
@Table(name = "ConcreteIngredients")
public class ConcreteIngredient {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "concrete_drug_id")
    private int concreteDrugId;

    @Column(name = "ingredient_id")
    private int ingredientId;

    @Column(name = "availability_date")
    private Date availabilityDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getConcreteDrugId() {
        return concreteDrugId;
    }

    public void setConcreteDrugId(int concreteDrugId) {
        this.concreteDrugId = concreteDrugId;
    }

    public int getIngredientId() {
        return ingredientId;
    }

    public void setIngredientId(int ingredientId) {
        this.ingredientId = ingredientId;
    }

    public Date getAvailabilityDate() {
        return availabilityDate;
    }

    public void setAvailabilityDate(Date availabilityDate) {
        this.availabilityDate = availabilityDate;
    }
}
