package edu.duke.ece651.shared.dao;

import edu.duke.ece651.shared.JDBCUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

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

    public Object queryScalar(String sql, Object... parameters) {

        Connection connection = null;
        try {
            connection = JDBCUtils.getConnection();
            return  qr.query(connection, sql, new ScalarHandler(), parameters);

        } catch (SQLException e) {
            throw  new RuntimeException(e);
        } finally {
            JDBCUtils.close(null, null, connection);
        }
    }

}
