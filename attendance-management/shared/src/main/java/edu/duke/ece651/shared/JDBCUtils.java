package edu.duke.ece651.shared;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class JDBCUtils {

    private static DataSource ds;
    private static Session session;

    static {
        try {
            setupSSHTunnel();
        } catch (Exception e) {
            System.err.println("Error setting up SSH tunnel: " + e.getMessage());
            e.printStackTrace();
        }

        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream("shared/src/druid.properties"));
            ds = DruidDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void setupSSHTunnel() throws Exception {
        int localPort = 5656; // Local port to forward through SSH tunnel
        String remoteHost = "localhost"; // MySQL host, accessible from SSH server
        int remotePort = 3306; // Remote MySQL port
        String sshUser = "vcm"; // SSH login username
        String sshPassword = "s3Uanlosyl"; // SSH login password
        String sshHost = "vcm-37940.vm.duke.edu"; // SSH server
        int sshPort = 22; // SSH port

        JSch jsch = new JSch();
        session = jsch.getSession(sshUser, sshHost, sshPort);
        session.setPassword(sshPassword);
        session.setConfig("StrictHostKeyChecking", "no");

        session.connect();
        session.setPortForwardingL(localPort, remoteHost, remotePort);
        //System.out.println("SSH Tunnel established.");
    }

    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

    public static void close(ResultSet resultSet, Statement statement, Connection connection) {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Call this method to close SSH tunnel when application stops
    public static void closeSSHTunnel() {
        if (session != null && session.isConnected()) {
            session.disconnect();
            //System.out.println("SSH Tunnel disconnected.");
        }
    }
}
