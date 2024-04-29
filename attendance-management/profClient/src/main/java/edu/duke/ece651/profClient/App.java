package edu.duke.ece651.profClient;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import java.io.*;
import java.net.Socket;

public class App extends Application {
    public static Socket socket;
    public static ObjectOutputStream out;
    public static ObjectInputStream in;

    @Override
    public void start(Stage primaryStage) throws Exception {
        socket = new Socket("localhost", 12345);
        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());
        
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/Login.fxml"));
        primaryStage.setTitle("Attendance Management - Professor Client");
        primaryStage.setScene(new Scene(root, 640, 480));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

