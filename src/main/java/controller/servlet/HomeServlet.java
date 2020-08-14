package controller.servlet;

import constant.*;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;

public class HomeServlet extends HttpServlet implements IUrnConstant, IProductErrorConstant {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext().getRequestDispatcher(HOME_VIEW).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }
}
