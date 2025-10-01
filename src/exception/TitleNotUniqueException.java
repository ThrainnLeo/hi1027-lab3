package exception;

public class TitleNotUniqueException extends RuntimeException{
    public TitleNotUniqueException(String message){
        super(message);
    }

}
