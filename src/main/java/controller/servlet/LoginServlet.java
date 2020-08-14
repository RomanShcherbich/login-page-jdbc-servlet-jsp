package controller.servlet;

import constant.*;
import dao.UserAccountDAO;
import lombok.extern.log4j.Log4j2;
import model.UserAccount;
import org.apache.commons.lang3.StringUtils;
import utils.SessionUtils;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.*;

@Log4j2
public class LoginServlet extends HttpServlet implements IUrnConstant, IServletErrorConstant {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext().getRequestDispatcher(LOGIN_VIEW).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userName = req.getParameter("userName");
        String password = req.getParameter("password");
        boolean remember = req.getParameter("rememberMe") != null;
        UserAccount authenticatedUser = null;
        boolean hasError = false;
        String errorString = null;
        if (userName == null || userName.trim().length() == 0 || password == null || password.trim().length() == 0) {
            hasError = true;
            errorString = LOGIN_NEED_CRED_ERROR;
            log.warn(errorString);
        } else {
            Connection connection = SessionUtils.getStoredConnection(req);
            try {
                UserAccountDAO dao = new UserAccountDAO(connection);
                authenticatedUser = dao.read(userName, password);
                if (authenticatedUser == null) {
                    hasError = true;
                    errorString = LOGIN_INVALID_CRED_ERROR;
                    log.warn(errorString);
                }
            } catch (SQLException exception) {
                exception.printStackTrace();
                hasError = true;
                errorString = exception.getMessage();
                log.warn(errorString);
            }
        }
        if (hasError) {
            authenticatedUser = new UserAccount();
            authenticatedUser.setUserName(userName);
            authenticatedUser.setPassword(password);
            req.setAttribute("user", authenticatedUser);
            req.setAttribute("errorString", errorString);
            getServletContext().getRequestDispatcher(LOGIN_VIEW).forward(req, resp);
        } else {
            HttpSession session = req.getSession();
            SessionUtils.storeAuthenticatedUser(session, authenticatedUser);
            if (remember) {
                SessionUtils.storeUserCookie(resp, authenticatedUser);
            } else {
                SessionUtils.deleteUserCookie(resp);
            }
            resp.sendRedirect(StringUtils.join(req.getContextPath(), USER_INFO_URL_PATTERN));
        }
    }

}
