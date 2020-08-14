package utils;

import constant.*;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class ServletUtils implements IServletErrorConstant {

    private static final String NOT_EMPTY_STRING_REGEX_PATTERN = "\\w+";

    public static boolean isFieldValid(Object field) throws Exception {
        return isFieldValid(field, FieldType.TEXT);
    }

    public static boolean isFieldValid(Object field, FieldType fieldType) throws Exception {
        String fieldString = String.valueOf(field).trim();
        return switch (fieldType) {
            case FLOAT -> isFloatValid(fieldString);
            case TEXT -> isTextFieldValid(fieldString);
            case ALPHANUMERICAL -> isAlphanumericalFieldValid(fieldString);
            default -> throw new Exception(String.format(CANNOT_HANDLE_FIELD_TYPE_ERROR_PATTERN, field));
        };
    }

    public static boolean isAlphanumericalFieldValid(String field) {
        return (field != null && field.matches(NOT_EMPTY_STRING_REGEX_PATTERN));
    }

    public static boolean isTextFieldValid(String field) {
        return (field != null && !field.isEmpty());
    }

    private static boolean isFloatValid(String floatField) {
        try {
            Float.valueOf(floatField);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
