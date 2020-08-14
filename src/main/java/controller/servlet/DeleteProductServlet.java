package controller.servlet;

import constant.*;
import dao.ProductDAO;
import org.apache.commons.lang3.StringUtils;
import utils.SessionUtils;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.*;

public class DeleteProductServlet extends HttpServlet implements IUrnConstant, IProductErrorConstant {

    private String errorString = "";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String code = req.getParameter("code");
        if (code != null) {
            Connection connection = SessionUtils.getStoredConnection(req);
            ProductDAO dao = new ProductDAO(connection);
            try {
                dao.delete(code);
                resp.sendRedirect(StringUtils.join(req.getContextPath(), PRODUCT_LIST_URL_PATTERN));
                return;
            } catch (SQLException exception) {
                errorString = exception.getMessage();
            }
        } else {
            errorString = DELETE_ERROR;
        }
        if (!errorString.isEmpty()) {
            req.setAttribute("errorString", errorString);
            resp.sendRedirect(StringUtils.join(req.getContextPath(), PRODUCT_LIST_URL_PATTERN));
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
