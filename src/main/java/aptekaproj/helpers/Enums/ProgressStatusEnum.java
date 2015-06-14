package aptekaproj.helpers.Enums;

/**
 * Created by Admin on 06.06.2015.
 */
public enum ProgressStatusEnum {
    CREATED("СОЗДАН"),
    UPDATED("ОБНОВЛЕН"),
    IN_PROCESS("В ПРОЦЕССЕ ИЗГОТОВЛЕНИЯ"),
    DONE("ЗАВЕРШЕН");

    private String displayName;
    ProgressStatusEnum(String displayName){
        this.displayName = displayName;
    }

    @Override
    public String toString(){
        return displayName;
    }
}
