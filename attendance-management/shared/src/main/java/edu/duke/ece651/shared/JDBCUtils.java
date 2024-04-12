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

/**
 * Utility class for managing database connections and SSH tunnels.
 * This class initializes a connection pool using Alibaba's Druid and sets up an SSH tunnel for secure database access.
 */
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
            properties.load(new FileInputStream("src/druid.properties"));
            ds = DruidDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Sets up an SSH tunnel to allow secure access to a remote MySQL server.
     * This method uses JSch to establish a connection to the SSH server and forwards a local port to the remote MySQL server port.
     * @throws Exception if any error occurs during the setup of SSH tunnel.
     */
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

    /**
     * Obtains a database connection from the connection pool.
     * @return an object to interact with the database.
     * @throws SQLException if a database access error occurs or the connection is closed.
     */
    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

    /**
     * Closes the provided ResultSet, Statement, and Connection resources.
     * This method ensures that all database resources are closed to avoid resource leaks.
     * @param resultSet the ResultSet to close.
     * @param statement the Statement to close.
     * @param connection the Connection to close.
     */
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

    /**
     * Closes the SSH tunnel when the application stops.
     * This method should be called to properly disconnect and release resources associated with the SSH tunnel.
     */
    // Call this method to close SSH tunnel when application stops
    public static void closeSSHTunnel() {
        if (session != null && session.isConnected()) {
            session.disconnect();
            //System.out.println("SSH Tunnel disconnected.");
        }
    }
}
