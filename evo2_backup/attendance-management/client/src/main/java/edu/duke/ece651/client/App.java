/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package edu.duke.ece651.client;

import java.io.BufferedReader;
import java.io.*;
import java.net.*;
import java.util.*;

public class App {
    public String getGreeting() {
        return "Hello World!";
    }

    public static void reveiveFile(BufferedReader input, Scanner scanner) throws Exception {
        // 读取服务器发来的文件名
        String fileName = input.readLine();
        System.out.println("File to receive: " + fileName);

        // 读取用户指定的保存路径
        System.out.println("Enter the path to save the file: ");
        String savePath = scanner.nextLine();

        // 接收文件内容并保存
        try (BufferedWriter fileWriter = new BufferedWriter(new FileWriter(savePath + File.separator + fileName))) {
            String line;
            while (!(line = input.readLine()).equals("endOfFile")) {
                // 注意：如果文件是二进制的，这里需要进行相应的处理，如base64解码
                fileWriter.write(line);
                fileWriter.newLine();
            }
            System.out.println("File has been received and saved to " + savePath);
        }
    }

    public static void main(String[] args) throws IOException {
//        try {
//            ClientSocketHandler client = new ClientSocketHandler();
//            String host = "localhost";
//            int port = 12345;
//            String testxml = "<request><message>Hello from Can Pei</message></request>";
//
//            client.connectToServer(host, port);
//            client.sendXmlToServer(testxml);
//            client.readResponseFromServer();
//            client.closeConnection();
//        } catch (Exception e){
//            System.out.println(e.getMessage());
//        }
        String host = "vcm-37924.vm.duke.edu";
        //String host = "localhost";
        int port = 12345;

        try (
                Socket socket = new Socket(host, port);
                BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                BufferedWriter output = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                Scanner scanner = new Scanner(System.in)
        ) {
            String fromServer;
            boolean flag = true;
            while (flag) {
                while ((fromServer = input.readLine()) != null && !fromServer.isEmpty()) {
                    System.out.println("Server says: " + fromServer);
                    if ("endConnection".equalsIgnoreCase(fromServer)) {
                        flag = false;
                        break;
                    }
                    if(fromServer.equals("startOfFile")){
                        try {
                            reveiveFile(input, scanner);
                        } catch (Exception e){
                            System.out.println(e.getMessage());
                        }
                    }
                }
                if (flag == false) break;
                System.out.print("Enter response: ");
                String userResponse = scanner.nextLine();
                output.write(userResponse + "\n");
                output.flush();

                if ("endConnection".equalsIgnoreCase(userResponse)) {
                    flag = false;
                    break;
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

    }
}
