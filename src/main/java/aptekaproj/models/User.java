package aptekaproj.models;

import org.springframework.data.repository.CrudRepository;

import javax.persistence.*;
import javax.transaction.Transactional;

@Entity(name = "models.Rolies.Users")
@Table(name = "users")
public class User {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer Id;

    @Column(name = "login")
    private String Login;

    @Column(name = "role_id")
    private int RoleId;

    @Column(name = "full_name")
    private String FullName;

    @Column(name = "hash")
    private String Hash;

    @Column(name = "salt")
    private String Salt;

    @Column(name = "address")
    private String Address;

    @Column(name = "medical_policy_number")
    private String MedicalPolicyNumber;

    @Column(name = "email")
    private String Email;

    @Column(name = "name")
    private String Name;

    public User(){

    }

    public User(int id){
        this.Id = id;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getLogin() {
        return Login;
    }

    public void setLogin(String login) {
        Login = login;
    }

    public int getRoleId() {
        return RoleId;
    }

    public void setRoleId(int roleId) {
        RoleId = roleId;
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }

    public String getHash() {
        return Hash;
    }

    public void setHash(String hash) {
        Hash = hash;
    }

    public String getSalt() {
        return Salt;
    }

    public void setSalt(String salt) {
        Salt = salt;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getMedicalPolicyNumber() {
        return MedicalPolicyNumber;
    }

    public void setMedicalPolicyNumber(String medicalPolicyNumber) {
        MedicalPolicyNumber = medicalPolicyNumber;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }




}
