package aptekaproj.helpers.exeptions;

/**
 * Created by Admin on 21.06.2015.
 */
public class ProcessException extends Exception {
    public ProcessException(){}
    public ProcessException(String message){super(message);}
    public ProcessException(Throwable throwable){super(throwable);}
    public ProcessException(String message, Throwable throwable){super(message,throwable);}
}
