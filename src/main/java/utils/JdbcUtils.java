package utils;

import lombok.extern.log4j.Log4j2;

import java.sql.*;

@Log4j2
public class JdbcUtils {

    public static Connection getConnection(String url, String username, String password, String driverClassName)
            throws SQLException {
        Connection connection = null;
        try {
            Class.forName(driverClassName);
            connection = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException e) {
            String message = String.format("Can't find class for name [%s]", driverClassName);
            log.error(message);
            throw new SQLException(message, e);
        } catch (SQLException e) {
            String message = String.format("Can't create connection to [%s]", url);
            log.error(message);
            throw new SQLException(message, e);
        }
        return connection;
    }

    public static void closeQuietly(Connection connection) throws Exception {
        try {
            connection.close();
        } catch (Exception e) {
            String message = String.format("Can't close connection to database." +
                                           "\nException [%s].",e.toString());
            log.error(message);
            throw new Exception(message, e);
        }
    }

    public static void rollbackQuietly(Connection connection) throws SQLException {
        try {
            connection.rollback();
        } catch (SQLException e) {
            String message = "Can't rollback a transaction to database";
            log.error(message);
            throw new SQLException(message, e);
        }
    }

}
