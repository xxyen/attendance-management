package edu.duke.ece651.profClient.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class HomePageController {
    @FXML
    private Label welcomeLabel;

    public void setProfessorName(String name) {
        welcomeLabel.setText("Welcome, Professor " + name + "!");
    }

    @FXML
    protected void handleManipulateSections(ActionEvent event) {
        System.out.println("Manipulate on your sections button clicked");
    }

    @FXML
    protected void handleLogout(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Login.fxml"));
            Parent root = loader.load();
            
            Stage stage = (Stage) welcomeLabel.getScene().getWindow();
            stage.setScene(new Scene(root, 640, 480));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
