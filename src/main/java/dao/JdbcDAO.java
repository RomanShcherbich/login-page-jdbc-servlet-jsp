package dao;

import lombok.extern.log4j.Log4j2;

import java.sql.*;

@Log4j2
public class JdbcDAO<T> {

    protected String SELECT_ALL_STATEMENT;
    protected String SELECT_MAX_ID_STATEMENT;

    public JdbcDAO(String table) {
        this.SELECT_ALL_STATEMENT = String.format("SELECT * FROM %s;\n", table);
        this.SELECT_MAX_ID_STATEMENT = String.format("SELECT MAX(id) FROM %s;\n", table);
    }

    protected int getMaxId(Connection connection) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement(SELECT_MAX_ID_STATEMENT)) {
            log.info(SELECT_MAX_ID_STATEMENT);
            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) {
            String message = String.format("Can't execute [%s]", SELECT_MAX_ID_STATEMENT);
            log.error(message);
            throw new SQLException(message, e);
        }
    }

}
