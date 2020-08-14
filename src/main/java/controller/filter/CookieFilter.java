package controller.filter;

import dao.UserAccountDAO;
import lombok.extern.log4j.Log4j2;
import model.UserAccount;
import utils.SessionUtils;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.*;

@Log4j2
public class CookieFilter implements Filter {

    private static final String COOKIES_ARE_CHECKED_ATTRIBUTE_KEY = "COOKIE_CHECKED";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpSession session = request.getSession();
        UserAccount userFromSession = SessionUtils.getAuthenticatedUser(session);
        if (userFromSession != null) {
            session.setAttribute(COOKIES_ARE_CHECKED_ATTRIBUTE_KEY, true);
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        Connection connection = SessionUtils.getStoredConnection(servletRequest);
        Boolean checked = (Boolean) session.getAttribute(COOKIES_ARE_CHECKED_ATTRIBUTE_KEY);
        if(checked == null && connection != null) {
            String username = SessionUtils.getUserNameFromCookie(request);
            if (username != null) {
                try {
                    UserAccountDAO dao = new UserAccountDAO(connection);
                    UserAccount user = dao.read(username);
                    SessionUtils.storeAuthenticatedUser(session, user);
                } catch (SQLException exception) {
                    log.error(String.format("Cannot select user [%s]", username));
                    exception.printStackTrace();
                }
                session.setAttribute(COOKIES_ARE_CHECKED_ATTRIBUTE_KEY, true);
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
    }

}
