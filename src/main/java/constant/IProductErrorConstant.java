package constant;

public interface IProductErrorConstant {

    String SELECT_ERROR = "Can't read products from database";
    String UPDATE_ERROR = "Can't update product!";
    String UPDATE_ERROR_PATTERN = "Can't update product with code '%s'!";
    String DELETE_ERROR = "Can't delete selected product!";
    String INSERT_ERROR = "Can't insert product to database!";
    String CODE_PRODUCT_FIELD_ERROR_MESSAGE = "Product code is invalid!";
    String NAME_PRODUCT_FIELD_ERROR_MESSAGE = "Product name is invalid!";
    String PRICE_PRODUCT_FIELD_ERROR_MESSAGE = "Product price is invalid!";

}
