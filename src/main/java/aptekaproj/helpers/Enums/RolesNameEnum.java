package aptekaproj.helpers.Enums;

/**
 * Created by Admin on 06.06.2015.
 */
public enum RolesNameEnum {
    DOCTOR("Doctor"),
    PATIENT("Patient");

    private String displayRoleName;
    RolesNameEnum(String displayRoleName){
        this.displayRoleName = displayRoleName;
    }

    @Override
    public String toString(){
        return displayRoleName;
    }
}
