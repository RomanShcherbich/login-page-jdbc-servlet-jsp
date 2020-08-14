package decorator;

import decorator.html.*;
import model.Product;
import org.apache.commons.lang3.StringUtils;
import utils.JaxbUtils;

import javax.xml.bind.JAXBException;
import java.util.*;

public class ProductRowDecorator {

    public static String createProductHtmlAdminRow(Product product) {
        String code = product.getCode();
        String name = product.getName();
        String price = String.valueOf(product.getPrice());
        AHtml aEdit = new AHtml("Edit", StringUtils.join("editProduct?code=", code));
        AHtml aDelete = new AHtml("Delete", StringUtils.join("deleteProduct?code=", code));
        TdHtml tdEdit = TdHtml.builder()
                .a(aEdit)
                .build();
        TdHtml tdDelete = TdHtml.builder()
                .a(aDelete)
                .build();
        TdHtml tdCode = TdHtml.builder()
                .div(code)
                .build();
        TdHtml tdName = TdHtml.builder()
                .div(name)
                .build();
        TdHtml tdPrice = TdHtml.builder()
                .div(price)
                .build();
        List<TdHtml> tdHtmlList = Arrays.asList(tdCode, tdName, tdPrice, tdEdit, tdDelete);
        TrHtml trHtml = new TrHtml(tdHtmlList);
        String result = null;
        try {
            result = JaxbUtils.xmlToString(trHtml);
        } catch (JAXBException e) {
            e.printStackTrace();
            System.out.println("Decorator exception!");
        }
        return result;
    }

    public static String createProductHtmlCustomerRow(Product product) {
        String code = product.getCode();
        String name = product.getName();
        String price = String.valueOf(product.getPrice());
        TdHtml tdCode = TdHtml.builder()
                .div(code)
                .build();
        TdHtml tdName = TdHtml.builder()
                .div(name)
                .build();
        TdHtml tdPrice = TdHtml.builder()
                .div(price)
                .build();
        List<TdHtml> tdHtmlList = Arrays.asList(tdCode, tdName, tdPrice);
        TrHtml trHtml = new TrHtml(tdHtmlList);
        String result = null;
        try {
            result = JaxbUtils.xmlToString(trHtml);
        } catch (JAXBException e) {
            e.printStackTrace();
            System.out.println("Decorator exception!");
        }
        return result;
    }

}
