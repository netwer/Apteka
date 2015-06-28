package aptekaproj.helpers.enums;

/**
 * Created by Admin on 06.06.2015.
 */
public enum RolesNameEnum {
    DOCTOR("DOCTOR"),
    PATIENT("PATIENT"),
    PHARMACIST("PHARMACIST"),
    ADMIN("ADMIN"),
    APOTHECARY("APOTHECARY");

    private String displayRoleName;
    RolesNameEnum(String displayRoleName){
        this.displayRoleName = displayRoleName;
    }

    @Override
    public String toString(){
        return displayRoleName;
    }
}
