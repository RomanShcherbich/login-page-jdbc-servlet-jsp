package controller.servlet;

import constant.*;
import dao.ProductDAO;
import decorator.ProductRowDecorator;
import exception.ApplicationException;
import model.*;
import utils.SessionUtils;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

public class ProductListServlet extends HttpServlet
        implements IUrnConstant, IModelConstant, IProductErrorConstant, IRequestAttribute {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Connection connection = SessionUtils.getStoredConnection(req);
        ProductDAO dao = new ProductDAO(connection);
        List<Product> productList = new ArrayList<>();
        try {
            productList = dao.readAll();
        } catch (SQLException exception) {
            exception.printStackTrace();
            ApplicationException.throwWithLog(exception, SELECT_ERROR);
        }
        UserAccount userAccount = SessionUtils.getAuthenticatedUser(req.getSession());
        if (userAccount.getRole().equals(ROLE_ADMIN)) {
            String productListHtml = productList.stream()
                    .map(ProductRowDecorator::createProductHtmlAdminRow)
                    .collect(Collectors.joining());
            req.setAttribute(PRODUCT_LIST_HTML_KEY, productListHtml);
            getServletContext().getRequestDispatcher(PRODUCT_LIST_ADMIN_VIEW).forward(req, resp);
        } else {
            String productListHtml = productList.stream()
                    .map(ProductRowDecorator::createProductHtmlCustomerRow)
                    .collect(Collectors.joining());
            req.setAttribute(PRODUCT_LIST_HTML_KEY, productListHtml);
            getServletContext().getRequestDispatcher(PRODUCT_LIST_CUSTOMER_VIEW).forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
