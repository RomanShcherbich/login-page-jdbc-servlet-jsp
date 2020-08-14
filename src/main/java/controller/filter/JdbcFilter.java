package controller.filter;

import exception.ApplicationException;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import utils.*;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.sql.*;
import java.util.*;

@Log4j2
public class JdbcFilter implements Filter {

    private static final String ALL_PATH_URL_PATTERN = "/*";
    private HttpServletRequest request;
    private String urlPattern;
    private String mySqlUrl;
    private String mySqlUsername;
    private String mySqlPassword;
    private String mySqlDriverClassName;
    private String connectionErrorMessage = "";

    @Override
    public void init(FilterConfig filterConfig) {
        log.info("JDBC Filter has been initiated");
        mySqlUrl = filterConfig.getInitParameter("jdbc.url");
        mySqlUsername = filterConfig.getInitParameter("jdbc.user");
        mySqlPassword = filterConfig.getInitParameter("jdbc.password");
        mySqlDriverClassName = filterConfig.getInitParameter("jdbc.driver.mysql");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        request = (HttpServletRequest) servletRequest;
        if (needJDBC()) {
            log.info(String.format("Open connection for URN [%s].", urlPattern));
            Connection connection = null;
            try {
                connection = JdbcUtils.getConnection(mySqlUrl, mySqlUsername, mySqlPassword, mySqlDriverClassName);
                autoCommit(connection);
                SessionUtils.storeConnection(request, connection);
                log.info("Database connection has been added to the current session");
                filterChain.doFilter(servletRequest, servletResponse);
                connection.commit();
            } catch (Exception exception) {
                rollback(connection, exception);
            } finally {
                close(connection);
            }
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    private boolean needJDBC() {
        String servletPath = request.getServletPath();
        String pathInfo = request.getPathInfo();
        urlPattern = servletPath;
        if (pathInfo != null) {
            urlPattern = servletPath + ALL_PATH_URL_PATTERN;
        }
        log.info(String.format("Checking URN [%s] permissions to open database connection.", urlPattern));
        Map<String, ? extends ServletRegistration> servletRegistration = request.getServletContext()
                .getServletRegistrations();
        Collection<? extends ServletRegistration> webAppServletList = servletRegistration.values();
        for (ServletRegistration sr :
                webAppServletList) {
            Collection<String> mappings = sr.getMappings();
            if (mappings.contains(urlPattern)) {
                return true;
            }
        }
        log.info(String.format("URN [%s] doesn't have permission to get database access.", urlPattern));
        return false;
    }

    private void autoCommit(Connection connection ) throws Exception {
        if (connection != null) {
            connection.setAutoCommit(false);
        } else {
            String message = "Connection is null.";
            log.error(message);
            throw new Exception(message);
        }
    }

    private void rollback(Connection connection, Exception exception) throws ServletException {
        connectionErrorMessage = "Opening connection to database has been failed.";
        try {
            if (connection != null) {
                JdbcUtils.rollbackQuietly(connection);
            }
        } catch (Exception throwables) {
            connectionErrorMessage = StringUtils.join("Connection to database cannot be rolled back.",
                    "\n", connectionErrorMessage);
            log.error(connectionErrorMessage);
            ApplicationException.throwWithLog(exception, connectionErrorMessage);
        }
        log.error(connectionErrorMessage);
        ApplicationException.throwWithLog(exception, connectionErrorMessage);
    }

    private void close(Connection connection) throws ServletException {
        try {
            if (connection != null) {
                JdbcUtils.closeQuietly(connection);
                log.info("Connection to database has been closed");
            }
        } catch (Exception throwables) {
            ApplicationException.throwWithLog(throwables, connectionErrorMessage);
        }
    }

    @Override
    public void destroy() {

    }

}
