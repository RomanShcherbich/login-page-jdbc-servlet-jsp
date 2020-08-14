package controller.servlet;

import constant.*;
import dao.ProductDAO;
import exception.*;
import lombok.extern.log4j.Log4j2;
import model.Product;
import org.apache.commons.lang3.StringUtils;
import utils.*;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.*;

@Log4j2
public class CreateProductServlet extends HttpServlet
        implements IUrnConstant, IProductErrorConstant, IServletErrorConstant {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext().getRequestDispatcher(PRODUCT_CREATE_VIEW).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String errorString = "";
        String code = req.getParameter("code");
        String name = req.getParameter("name");
        String priceString = req.getParameter("price").replace(",",".");
        errorString += ProductFieldUtils.isProductCodeValid(code);
        errorString += ProductFieldUtils.isProductNameValid(name);
        errorString += ProductFieldUtils.isProductPriceValid(priceString);
        if (errorString.isEmpty()) {
            Connection connection = SessionUtils.getStoredConnection(req);
            try {
                Product product = new Product(code, name, Float.valueOf(priceString));
                ProductDAO dao = new ProductDAO(connection);
                dao.create(product);
                resp.sendRedirect(StringUtils.join(req.getContextPath(), PRODUCT_LIST_URL_PATTERN));
                return;
            } catch (DuplicateException exception) {
                errorString += StringUtils.join("\n", exception.getMessage());
            } catch (SQLException exception) {
                exception.printStackTrace();
                ApplicationException.throwWithLog(exception, INSERT_ERROR);
            }
        }
        if (!errorString.isEmpty()) {
            errorString += StringUtils.join("\n", SYMBOLS_ERROR_ALERT);
            req.setAttribute("code", code);
            req.setAttribute("name", name);
            req.setAttribute("price", priceString);
            req.setAttribute("errorString", errorString);
            getServletContext().getRequestDispatcher(PRODUCT_CREATE_VIEW).forward(req, resp);
        }
    }

}
