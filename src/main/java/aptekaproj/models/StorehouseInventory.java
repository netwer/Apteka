package aptekaproj.models;

import javax.persistence.*;

/**
 * Created by Admin on 25.04.2015.
 */
@Entity(name = "models.StorehouseInventory")
@Table(name = "StorehouseInventory")
public class StorehouseInventory {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int Id;

    @Column(name = "id")
    private int material_id;

    @Column(name = "id")
    private int count;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getMaterial_id() {
        return material_id;
    }

    public void setMaterial_id(int material_id) {
        this.material_id = material_id;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
