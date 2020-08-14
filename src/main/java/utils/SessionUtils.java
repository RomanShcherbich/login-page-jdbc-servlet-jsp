package utils;

import constant.IRequestAttribute;
import lombok.extern.log4j.Log4j2;
import model.UserAccount;
import javax.servlet.ServletRequest;
import javax.servlet.http.*;
import java.sql.Connection;

@Log4j2
public class SessionUtils implements IRequestAttribute {

    public static void storeConnection(ServletRequest request, Connection connection) {
        request.setAttribute(CONNECTION_KEY, connection);
    }

    public static Connection getStoredConnection(ServletRequest request) {
        Connection connection = (Connection) request.getAttribute(CONNECTION_KEY);
        return connection;
    }

    public static void storeAuthenticatedUser(HttpSession session, UserAccount user) {
        session.setAttribute(AUTHENTICATED_USER_KEY, user);
    }

    public static UserAccount getAuthenticatedUser(HttpSession session) {
        return (UserAccount) session.getAttribute(AUTHENTICATED_USER_KEY);
    }

    public static void storeUserCookie(HttpServletResponse response, UserAccount user) {
        log.info("Store user cookie");
        Cookie cookieUserName = new Cookie(USER_NAME_KEY, user.getUserName());
        cookieUserName.setMaxAge(24 * 60 * 60);
        response.addCookie(cookieUserName);
    }

    public static String getUserNameFromCookie(HttpServletRequest request) {
        Cookie[] cookieArray = request.getCookies();
        if (cookieArray != null) {
            for (Cookie cookie : cookieArray) {
                if (USER_NAME_KEY.equals(cookie.getName())){
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    public static void deleteUserCookie(HttpServletResponse response) {
        Cookie cookie = new Cookie(USER_NAME_KEY, null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }

}
