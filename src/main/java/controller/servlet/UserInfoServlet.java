package controller.servlet;

import constant.*;
import model.UserAccount;
import org.apache.commons.lang3.StringUtils;
import utils.SessionUtils;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;

public class UserInfoServlet extends HttpServlet implements IUrnConstant, IServletErrorConstant {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        UserAccount authenticatedUser = SessionUtils.getAuthenticatedUser(session);
        if (authenticatedUser == null) {
            resp.sendRedirect(StringUtils.join(req.getContextPath(), LOGIN_URL_PATTERN));
            return;
        }
        req.setAttribute("user", authenticatedUser);
        getServletContext().getRequestDispatcher(USER_INFO_VIEW).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }
}
