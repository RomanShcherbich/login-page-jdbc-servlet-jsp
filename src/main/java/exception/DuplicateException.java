package exception;

import org.apache.commons.lang3.StringUtils;

import java.sql.*;

public class DuplicateException extends SQLIntegrityConstraintViolationException {

    private String code;

    public DuplicateException(SQLIntegrityConstraintViolationException exception, String code) {
        super(exception);
        this.code = code;
    }

    @Override
    public String getMessage() {
        return StringUtils.join("Product with code '",code,"' already exists!");
    }

}
