package controller.servlet;

import constant.*;
import dao.ProductDAO;
import exception.*;
import model.Product;
import org.apache.commons.lang3.StringUtils;
import utils.*;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.*;

public class EditProductServlet extends HttpServlet
        implements IUrnConstant, IProductErrorConstant, IServletErrorConstant{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String errorString = "";
        String code = req.getParameter("code");
        errorString += ProductFieldUtils.isProductCodeValid(code);
        if (errorString.isEmpty()) {
            try {
                Connection connection = SessionUtils.getStoredConnection(req);
                ProductDAO dao = new ProductDAO(connection);
                Product product = dao.read(code);
                req.setAttribute("code", code);
                req.setAttribute("name", product.getName());
                req.setAttribute("price", String.valueOf(product.getPrice()));
                getServletContext().getRequestDispatcher(PRODUCT_EDIT_VIEW).forward(req, resp);
            } catch (SQLException exception) {
                errorString += String.format(UPDATE_ERROR_PATTERN, code);
            }
        }
        if (!errorString.isEmpty()) {
            req.setAttribute("errorString", errorString);
            resp.sendRedirect(StringUtils.join(req.getContextPath(), PRODUCT_LIST_URL_PATTERN));
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String errorString = "";
        String code = req.getParameter("code");
        String newCode = req.getParameter("newCode");
        String name = req.getParameter("name");
        String priceString = req.getParameter("price").replace(",",".");
        errorString += ProductFieldUtils.isProductCodeValid(newCode);
        errorString += ProductFieldUtils.isProductNameValid(name);
        errorString += ProductFieldUtils.isProductPriceValid(priceString);
        if (errorString.isEmpty()) {
            Connection connection = SessionUtils.getStoredConnection(req);
            try {
                Product product = new Product(newCode, name, Float.valueOf(priceString));
                ProductDAO dao = new ProductDAO(connection);
                dao.update(product, code);
                resp.sendRedirect(StringUtils.join(req.getContextPath(), PRODUCT_LIST_URL_PATTERN));
                return;
            } catch (DuplicateException exception) {
                errorString += StringUtils.join("\n", exception.getMessage());
            } catch (SQLException exception) {
                errorString += StringUtils.join("\n", UPDATE_ERROR);
            }
        }
        if (!errorString.isEmpty()) {
            errorString += StringUtils.join("\n", SYMBOLS_ERROR_ALERT);
            req.setAttribute("code", code);
            req.setAttribute("name", name);
            req.setAttribute("price", priceString);
            req.setAttribute("errorString", errorString);
            getServletContext().getRequestDispatcher(PRODUCT_EDIT_VIEW).forward(req, resp);
        }
    }

}
