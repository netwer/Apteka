package aptekaproj.models;

import javax.persistence.*;

@Entity(name = "models.Rolies.Users")
@Table(name = "users")
public class User {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "login")
    private String login;

    @Column(name = "role_id")
    private int roleId;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "hash")
    private String hash;

    @Column(name = "salt")
    private String salt;

    @Column(name = "address")
    private String address;

    @Column(name = "medical_policy_number")
    private String medicalPolicyNumber;

    @Column(name = "email")
    private String email;

    @Column(name = "name")
    private String name;

    public Role role;

    public User(){
    }

    public User(int id){
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMedicalPolicyNumber() {
        return medicalPolicyNumber;
    }

    public void setMedicalPolicyNumber(String medicalPolicyNumber) {
        this.medicalPolicyNumber = medicalPolicyNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }




}
