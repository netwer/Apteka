package aptekaproj.helpers.enums;

/**
 * Created by Admin on 06.06.2015.
 */
public enum RolesNameEnum {
    DOCTOR("Doctor"),
    PATIENT("Patient"),
    PHARMACIES("Pharmacies"),
    ADMIN("Admin"),
    APOTHECARY("Apothecary");

    private String displayRoleName;
    RolesNameEnum(String displayRoleName){
        this.displayRoleName = displayRoleName;
    }

    @Override
    public String toString(){
        return displayRoleName;
    }
}
