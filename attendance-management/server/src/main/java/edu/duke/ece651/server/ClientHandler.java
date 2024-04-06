package edu.duke.ece651.server;

import edu.duke.ece651.shared.AttendanceOperator;
import edu.duke.ece651.shared.Course;
import edu.duke.ece651.shared.Professor;
import edu.duke.ece651.shared.*;

import java.io.*;
import java.net.Socket;

public class ClientHandler implements Runnable{
    private Socket clientSocket;

    private UserOperator userOperator = new UserOperator();

    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
    }

    public void mainLoop(BufferedReader input, PrintStream output){
        boolean flag = true;

        while (flag) {
            try {
                output.print("--------------------------------------------------------------------------------\n");
                output.print("Hello! Below are all the available actions:" +
                        "1. Log in.\n" +
                        "2. Exit this program.\n" +
                        "What do you want to do? Please type in the index number:\n");
                output.println("--------------------------------------------------------------------------------\n");

                int index = ReaderUtilities.readPositiveInteger(input);

                if (index == 1){
                    User user = signIn(input, output);
                    //todo:
                    //do Prof or Stu action

                    if (user.getUserType() == "professor"){
                        Professor p = (Professor) user;
                        output.println("Login successful. Welcome, Professor" + p.getName() + "!");
                        profLoop(input, output);
                    }

                    else if (user.getUserType() == "student"){
                        Student s = (Student) user;
                        output.println("Login successful. Welcome, Student" + s.getDisplayName() + "!");
                        stuLoop(input, output);
                    }

                }
                else if (index == 2){
                    output.println("Have a nice day! From ECE 651 team 6.");
                    flag = false;
                    break;
                }
                else {
                    throw new IllegalArgumentException("Invalid action number, please choose your action again!");
                }

            } catch (Exception e) {
                output.println(e.getMessage());
            }
        }
    }

    public User signIn(BufferedReader input, PrintStream output) throws Exception {
        output.println("Please enter your userid:");
        String userid = input.readLine();
        output.println("Please enter your password:");
        String password = input.readLine();

        User user = userOperator.signIn(userid, password);
        // 登录成功，发送欢迎信息
        //output.println("Login successful. Welcome, " + user.getUserid() + "!");

        return user;

    }

    public void profLoop(BufferedReader input, PrintStream output){
        boolean flag = true;

        while (flag) {
            try {
                output.print("--------------------------------------------------------------------------------\n");
                output.print("Below are all the available actions:" +
                        "1. Manipulate on your sections.\n" +
                        "2. Log out.\n" +
                        "What do you want to do? Please type in the index number:\n");
                output.println("--------------------------------------------------------------------------------\n");

                int index = ReaderUtilities.readPositiveInteger(input);

                if (index == 1){
                    //todo:
                    //display section list and choose

                }
                else if (index == 2){
                    output.println("Successfully log out!");
                    flag = false;
                    break;
                }
                else {
                    throw new IllegalArgumentException("Invalid action number, please choose your action again!");
                }

            } catch (Exception e) {
                output.println(e.getMessage());
            }
        }
    }

    public void stuLoop(BufferedReader input, PrintStream output){
        boolean flag = true;

        while (flag) {
            try {
                output.print("--------------------------------------------------------------------------------\n");
                output.print("Below are all the available actions:" +
                        "1. Manipulate on your sections.\n" +
                        "2. Log out.\n" +
                        "What do you want to do? Please type in the index number:\n");
                output.println("--------------------------------------------------------------------------------\n");

                int index = ReaderUtilities.readPositiveInteger(input);

                if (index == 1){
                    //todo:
                    //display section list and choose

                }
                else if (index == 2){
                    output.println("Successfully log out!");
                    flag = false;
                    break;
                }
                else {
                    throw new IllegalArgumentException("Invalid action number, please choose your action again!");
                }

            } catch (Exception e) {
                output.println(e.getMessage());
            }
        }
    }

    @Override
    public void run() {
        try (
                BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintStream output = new PrintStream(clientSocket.getOutputStream(), true)
        ) {
            mainLoop(input, output);

            // 处理完成后关闭连接
            clientSocket.close();
        } catch (Exception e) {
            System.err.println("Exception in client handler: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
