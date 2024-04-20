package edu.duke.ece651.server;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintStream;

public class sendFileService {
    public static void sendFile(PrintStream output, String filePath) throws Exception{
        output.println("startOfFile");

        //String filePath = "/home/wille/Desktop/test2.xml"; // 根据请求确定文件路径

        // 发送文件名给客户端
        File file = new File(filePath);
        output.println(file.getName());
        //output.flush();

        // 发送文件内容
        try (BufferedReader fileReader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = fileReader.readLine()) != null) {
                output.println(line);
            }
            //output.flush();
        }

        // 发送结束信号
        output.println("endOfFile");
        //output.flush();
    }
}
