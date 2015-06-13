package aptekaproj.models;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Admin on 28.05.2015.
 */
@Entity(name = "models.ConcreteIngredients")
@Table(name = "ConcreteIngredients")
public class ConcreteIngredients {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int Id;

    @Column(name = "concrete_drug_id")
    private int ConcreteDrugId;

    @Column(name = "ingredient_id")
    private int IngredientId;

    @Column(name = "availability_date")
    private Date AvailabilityDate;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getConcreteDrugId() {
        return ConcreteDrugId;
    }

    public void setConcreteDrugId(int concreteDrugId) {
        ConcreteDrugId = concreteDrugId;
    }

    public int getIngredientId() {
        return IngredientId;
    }

    public void setIngredientId(int ingredientId) {
        IngredientId = ingredientId;
    }

    public Date getAvailabilityDate() {
        return AvailabilityDate;
    }

    public void setAvailabilityDate(Date availabilityDate) {
        AvailabilityDate = availabilityDate;
    }
}
