package aptekaproj.models;

import javax.persistence.*;

/**
 * Created by Admin on 28.05.2015.
 */
@Entity(name = "models.ConcreteDrugs")
@Table(name = "ConcreteDrugs")
public class ConcreteDrugs {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int Id;

    @Column(name = "drug_id")
    private int DrugId;

    @Column(name = "PharmacyStaff_id")
    private int PharmacyStaffId;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getDrugId() {
        return DrugId;
    }

    public void setDrugId(int drugId) {
        DrugId = drugId;
    }

    public int getPharmacyStaffId() {
        return PharmacyStaffId;
    }

    public void setPharmacyStaffId(int pharmacyStaffId) {
        PharmacyStaffId = pharmacyStaffId;
    }
}