package edu.duke.ece651.shared.dao;

import edu.duke.ece651.shared.JDBCUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * The BasicDAO class provides basic data access operations using JDBC.
 * It includes methods for executing SQL queries and updates, handling transactions,
 * and retrieving results in various formats.
 *
 * @param <T> the type of objects handled by this DAO
 */
public class BasicDAO<T> { 

    private QueryRunner qr =  new QueryRunner();

    /**
     * Executes an SQL update statement with the given parameters.
     *
     * @param sql        the SQL statement to be executed
     * @param parameters the parameters to be set in the SQL statement
     * @return the number of rows affected by the update
     * @throws RuntimeException if an SQL exception occurs
     */
    public int update(String sql, Object... parameters) {
        Connection connection = null;
        try {
            connection = JDBCUtils.getConnection();
            int update = qr.update(connection, sql, parameters);
            return  update;
        } catch (SQLException e) {
           throw  new RuntimeException(e);
        } finally {
            JDBCUtils.close(null, null, connection);
        }

    }

    /**
     * Executes an SQL query and returns the result as a list of objects of type T.
     *
     * @param sql    the SQL query to be executed
     * @param clazz  the class of objects to be returned
     * @param parameters the parameters to be set in the SQL statement
     * @return a list of objects of type T
     * @throws RuntimeException if an SQL exception occurs
     */
    public List<T> queryMulti(String sql, Class<T> clazz, Object... parameters) {

        Connection connection = null;
        try {
            connection = JDBCUtils.getConnection();
            return qr.query(connection, sql, new BeanListHandler<T>(clazz), parameters);

        } catch (SQLException e) {
            throw  new RuntimeException(e);
        } finally {
            JDBCUtils.close(null, null, connection);
        }
    }


    /**
     * Executes an SQL query and returns a single result object of type T.
     *
     * @param sql    the SQL query to be executed
     * @param clazz  the class of the object to be returned
     * @param parameters the parameters to be set in the SQL statement
     * @return a single result object of type T, or null if no result is found
     * @throws RuntimeException if an SQL exception occurs
     */
    public T querySingle(String sql, Class<T> clazz, Object... parameters) {
        Connection connection = null;
        try {
            connection = JDBCUtils.getConnection();
            return  qr.query(connection, sql, new BeanHandler<T>(clazz), parameters);
        } catch (SQLException e) {
            throw  new RuntimeException(e); 
        } finally {
            JDBCUtils.close(null, null, connection);
        }
    }

    /**
     * Executes an SQL query and returns the result as a mapped representation (key-value pairs).
     *
     * @param sql    the SQL query to be executed
     * @param parameters the parameters to be set in the SQL statement
     * @return a mapped representation (key-value pairs) of the query result
     * @throws RuntimeException if an SQL exception occurs
     */
    public Map<String, Object> querySingleMapped(String sql, Object... parameters) {
        Connection connection = null;
        try {
            connection = JDBCUtils.getConnection();
            return qr.query(connection, sql, new MapHandler(), parameters);
        } catch (SQLException e) {
            throw  new RuntimeException(e); 
        } finally {
            JDBCUtils.close(null, null, connection);
        }
    }

    /**
     * Executes an SQL query and returns the result as a list of mapped representations (key-value pairs).
     *
     * @param sql    the SQL query to be executed
     * @param parameters the parameters to be set in the SQL statement
     * @return a list of mapped representations (key-value pairs) of the query result
     * @throws RuntimeException if an SQL exception occurs
     */
    public List<Map<String, Object>> queryMultiMapped(String sql, Object... parameters) {
        Connection connection = null;
        try {
            connection = JDBCUtils.getConnection();
            return qr.query(connection, sql, new MapListHandler(), parameters);
        } catch (SQLException e) {
            throw  new RuntimeException(e); 
        } finally {
            JDBCUtils.close(null, null, connection);
        }
    }


    /**
     * Executes an SQL insert statement and returns the generated key.
     *
     * @param sql    the SQL insert statement to be executed
     * @param parameters the parameters to be set in the SQL statement
     * @return the generated key
     * @throws RuntimeException if an SQL exception occurs
     */
    public long insertAndGetGeneratedKey(String sql, Object... parameters) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet generatedKeys = null;
        try {
            connection = JDBCUtils.getConnection();
            preparedStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            for (int i = 0; i < parameters.length; i++) {
                preparedStatement.setObject(i + 1, parameters[i]);
            }
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating item failed, no rows affected.");
            }
            generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                return generatedKeys.getLong(1);
            } else {
                throw new SQLException("Creating item failed, no ID obtained.");
            }
        } catch (SQLException e) {
           throw new RuntimeException(e);
        } finally {
            JDBCUtils.close(generatedKeys, preparedStatement, connection);
        }
    }

}
