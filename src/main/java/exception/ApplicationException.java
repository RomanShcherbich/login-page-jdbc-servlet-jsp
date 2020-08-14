package exception;

import org.apache.commons.lang3.StringUtils;
import javax.servlet.ServletException;

public class ApplicationException {

    public static void throwWithLog(Exception exception, String errorMessage) throws ServletException {
            errorMessage = StringUtils.join(exception.getMessage(), "\n", errorMessage);
            Exception exc = new ServletException(errorMessage, exception);
            throw new ServletException(exc);
    }

}
