package aptekaproj.models;

import javax.persistence.*;

/**
 * Created by Admin on 28.05.2015.
 */
@Entity(name = "models.PharmacyStaff")
@Table(name = "PharmacyStaff")
public class PharmacyStaff {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int Id;

    @Column(name = "user_id")
    private int UserId;

    @Column(name = "pharmacy_id")
    private int PharmacyId;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int userId) {
        UserId = userId;
    }

    public int getPharmacyId() {
        return PharmacyId;
    }

    public void setPharmacyId(int pharmacyId) {
        PharmacyId = pharmacyId;
    }
}
