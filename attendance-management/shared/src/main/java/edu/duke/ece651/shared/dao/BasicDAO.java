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


public class BasicDAO<T> { 

    private QueryRunner qr =  new QueryRunner();

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


    // public Object queryScalar(String sql, Object... parameters) {

    //     Connection connection = null;
    //     try {
    //         connection = JDBCUtils.getConnection();
    //         return  qr.query(connection, sql, new ScalarHandler(), parameters);

    //     } catch (SQLException e) {
    //         throw  new RuntimeException(e);
    //     } finally {
    //         JDBCUtils.close(null, null, connection);
    //     }
    // }

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
