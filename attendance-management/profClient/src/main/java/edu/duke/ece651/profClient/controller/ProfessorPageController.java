package edu.duke.ece651.profClient.controller;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import java.io.IOException;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.Node;

public class ProfessorPageController {
    private Stage mainStage;

    public void setMainStage(Stage mainStage) {
        this.mainStage = mainStage;
      }
        
    @FXML
    protected void handleStartNewSession() {
        System.out.println("StartNewSession clicked!");

    }

    @FXML
    protected void handleChangeAttendance() {
        System.out.println("ChangeAttendance clicked!" );

    }

    @FXML
    protected void handleExportRecords() {
        System.out.println("ExportRecords clicked!" );
    }

    @FXML
    protected void handleExit(ActionEvent event) throws IOException {
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/HomePage.fxml"));
        Parent root = loader.load();
        stage.setScene(new Scene(root, 640, 480));
        stage.show();
    }

}
