package edu.duke.ece651.shared;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class TestJDBCUtils {
    public static void main(String[] args) {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            connection = JDBCUtils.getConnection();
            System.out.println("Database connection established successfully.");

            statement = connection.createStatement();

            String sql = "SELECT * FROM course";
            resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                System.out.println("Record ID: " + resultSet.getString("course_id") + " Course Name: " + resultSet.getString("course_name"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.close(resultSet, statement, connection);
        }

        JDBCUtils.closeSSHTunnel();
    }
}
