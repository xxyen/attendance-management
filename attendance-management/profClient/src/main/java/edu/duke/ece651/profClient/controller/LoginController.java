package edu.duke.ece651.profClient.controller;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import java.io.*;
import java.util.HashMap;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.duke.ece651.profClient.App;

public class LoginController {
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label errorLabel;

    private ObjectMapper mapper = new ObjectMapper();

    @FXML
    private void handleLogin() throws IOException {
        String username = usernameField.getText();
        String password = passwordField.getText();

        String response = sendLoginDetails(username, password);
        if (!response.startsWith("The userid or password is invalid!")) {
            navigateToHomePage(response);
        } else {
            showAlert("Login Error", response);
        }
    }

    private String sendLoginDetails(String username, String password) throws IOException {
        HashMap<String, String> credentials = new HashMap<>();
        credentials.put("userid", username);
        credentials.put("password", password);

        String json = mapper.writeValueAsString(credentials);
        App.out.writeObject(json);
        App.out.flush();

        try {
            return (String) App.in.readObject();
        } catch (ClassNotFoundException e) {
            return "Error communicating with server.";
        }
    }

    private void navigateToHomePage(String professorName) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/HomePage.fxml"));
        Parent root = loader.load();
        HomePageController controller = loader.getController();
        controller.setProfessorName(professorName);
    
        Stage stage = (Stage) usernameField.getScene().getWindow();
        stage.setScene(new Scene(root, 648, 480));
        stage.show();
    }
    

    private void showAlert(String title, String content) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
