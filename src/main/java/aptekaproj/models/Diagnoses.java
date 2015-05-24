package aptekaproj.models;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Admin on 25.04.2015.
 */

@Entity(name = "models.Diagnoses")
@Table(name = "Diagnoses")
public class Diagnoses {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int Id;

    @Column(name = "patient_user_id")
    private int patient_user_id;

    @Column(name = "doctor_user_id")
    private int doctor_user_id;

    @Column(name = "recipe_id")
    private Integer recipe_id;

    @Column(name = "diagnosis")
    private String diagnosis;

    @Column(name = "complaints")
    private String complaints;

    @Column(name = "created_at")
    private Date created_at;

    public int getPatient_user_id() {
        return patient_user_id;
    }

    public void setPatient_user_id(int patient_user_id) {
        this.patient_user_id = patient_user_id;
    }

    public int getDoctor_user_id() {
        return doctor_user_id;
    }

    public void setDoctor_user_id(int doctor_user_id) {
        this.doctor_user_id = doctor_user_id;
    }

    public Integer getRecipe_id() {
        return recipe_id;
    }

    public void setRecipe_id(Integer recipe_id) {
        this.recipe_id = recipe_id;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getComplaints() {
        return complaints;
    }

    public void setComplaints(String complaints) {
        this.complaints = complaints;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }
}
