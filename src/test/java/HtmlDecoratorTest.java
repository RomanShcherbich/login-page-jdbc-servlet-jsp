import decorator.ProductRowDecorator;
import model.Product;
import org.junit.Test;

public class HtmlDecoratorTest {

    @Test
    public void productRowHtmlTest() {
        System.out.println(ProductRowDecorator.createProductHtmlAdminRow(
                new Product("TESTCODE01", "TESTNAME01", 1.1f)));
    }

}
