package aptekaproj.helpers.Enums;

/**
 * Created by Admin on 06.06.2015.
 */
public enum ProgressStatusEnum {
    CREATED("НОВЫЙ"),
    UPDATED("ОБНОВЛЕН"),
    IN_PROCESS("В ПРОЦЕССЕ ИЗГОТОВЛЕНИЯ"),
    WAITING_PROCESS("ОЖИДАЕТ ИЗГОТОВЛЕНИЯ"),
    PACKAGE("КОМПЛЕКТУЕТСЯ"),
    DONE("ГОТОВ"),
    ISSUED("ВЫДАН");

    private String displayName;
    ProgressStatusEnum(String displayName){
        this.displayName = displayName;
    }

    @Override
    public String toString(){
        return displayName;
    }
}
