//package edu.duke.ece651.client;
//
//import java.io.*;
//import java.net.Socket;
//
//public class ClientSocketHandler {
//    private Socket socket;
//    private PrintWriter output;
//    private BufferedReader input;
//
//    // 连接到服务器
//    public void connectToServer(String host, int port) throws IOException {
//        System.out.println("Begin to connect...");
//
//        this.socket = new Socket(host, port);
//        System.out.println("Connected to server at " + host + ":" + port);
//        // 初始化输出流和输入流
//        this.output = new PrintWriter(socket.getOutputStream(), true);
//        this.input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//    }
//
//    // 发送XML数据到服务器
//    public void sendXmlToServer(String xmlData) {
//        System.out.println("Sending XML to server: " + xmlData);
//        this.output.println(xmlData);
//        this.output.println(); // 发送一个空行表示消息结束
//    }
//
//    // 读取服务器的响应
//    public String readResponseFromServer() throws IOException {
//        StringBuilder response = new StringBuilder();
//        String line;
//        while ((line = this.input.readLine()) != null && !line.isEmpty()) {
//            response.append(line);
//        }
//        System.out.println("Received from server: " + response);
//        return response.toString();
//    }
//
//    // 关闭连接
//    public void closeConnection() throws IOException {
//        if (this.socket != null) {
//            this.socket.close();
//            System.out.println("Disconnected from server.");
//        }
//    }
//}
