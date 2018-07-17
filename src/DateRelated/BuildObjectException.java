package DateRelated;
import java.util.logging.Logger;

public class BuildObjectException extends Exception {
    public BuildObjectException(String exceptionString){
       Logger log=Logger.getLogger("BuildObjectExceptionLogger");
       log.info(exceptionString);
    }
}
