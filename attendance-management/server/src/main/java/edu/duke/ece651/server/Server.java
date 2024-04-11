package edu.duke.ece651.server;

import java.net.ServerSocket;
import java.net.Socket;
import edu.duke.ece651.shared.*;

public class Server {
    public static void main(String[] args) {
        // 创建任务
        WeeklyReporter task = new WeeklyReporter();
        // 在新线程中启动任务
        Thread thread = new Thread(task);
        thread.start();


        final int port = 12345; // 服务器监听的端口号
        try (ServerSocket serverSocket = new ServerSocket(port)) { // 使用try-with-resources确保资源被正确关闭
            System.out.println("Server listening on port " + port);

            while (true) {
                Socket clientSocket = serverSocket.accept(); // 等待客户端连接
                System.out.println("Client connected from " + clientSocket.getInetAddress().getHostAddress());

                // 为每个客户端创建一个新的线程
                new Thread(new ClientHandler(clientSocket)).start();
            }
        } catch (Exception e) {
            System.err.println("Server exception: " + e.getMessage());
            e.printStackTrace();
        }
    }

}
