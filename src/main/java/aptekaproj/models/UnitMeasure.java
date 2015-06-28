package aptekaproj.models;

import javax.persistence.*;

/**
 * Created by org.apteka on 28.06.2015.
 */
@Entity(name = "models.UnitMeasure")
@Table(name = "unit_measure")
public class UnitMeasure {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "unit")
    private String unit;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
