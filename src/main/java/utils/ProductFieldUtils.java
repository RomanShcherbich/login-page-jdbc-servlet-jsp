package utils;

import constant.*;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;

@Log4j2
public class ProductFieldUtils implements IProductErrorConstant{

    public static String isProductCodeValid(Object field) {
        String error = StringUtils.join("\n", CODE_PRODUCT_FIELD_ERROR_MESSAGE);
        try {
            return ServletUtils.isFieldValid(field, FieldType.ALPHANUMERICAL) ? ""
                    : error;
        } catch (Exception exception) {
            log.info(error);
            return error;
        }
    }

    public static String isProductNameValid(Object field) {
        String error = StringUtils.join("\n", NAME_PRODUCT_FIELD_ERROR_MESSAGE);
        try {
            return ServletUtils.isFieldValid(field, FieldType.TEXT) ? "" : error;
        } catch (Exception exception) {
            log.info(error);
            return error;
        }
    }

    public static String isProductPriceValid(Object field) {
        String error = StringUtils.join("\n", PRICE_PRODUCT_FIELD_ERROR_MESSAGE);
        try {
            return ServletUtils.isFieldValid(field, FieldType.FLOAT) ? "" : error;
        } catch (Exception exception) {
            log.info(error);
            return error;
        }
    }

}
