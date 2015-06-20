package aptekaproj.helpers.exeptions;

/**
 * Created by Admin on 20.06.2015.
 */
public class SaveUpdateException extends Exception {
    public SaveUpdateException(){}
    public SaveUpdateException(String message){super(message);}
    public SaveUpdateException(Throwable throwable){super(throwable);}
    public SaveUpdateException(String message, Throwable throwable){super(message,throwable);}
}
