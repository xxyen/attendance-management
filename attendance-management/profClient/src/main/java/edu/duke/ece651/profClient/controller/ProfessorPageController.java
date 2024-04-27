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
import edu.duke.ece651.profClient.App;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DialogPane;
import java.util.HashMap;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ProfessorPageController {    
    private ObjectMapper mapper = new ObjectMapper();

    @FXML
    protected void handleStartNewSession() {
        System.out.println("StartNewSession clicked!");

    }

    @FXML
    protected void handleChangeAttendance() {
        System.out.println("ChangeAttendance clicked!" );

    }

    @FXML
    protected void handleExportRecords(ActionEvent event)throws IOException {
        HashMap<String, String> command = new HashMap<>();
        command.put("action", "Export");
        String json = mapper.writeValueAsString(command);
        App.out.writeObject(json);
        App.out.flush();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ExportRecordPage.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root, 640, 480);
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
}


    @FXML
    protected void handleExit(ActionEvent event) throws IOException,ClassNotFoundException {
        HashMap<String, String> command = new HashMap<>();
        command.put("action", "Exit");
        String json = mapper.writeValueAsString(command);
        App.out.writeObject(json);
        App.out.flush();

        String response = (String) App.in.readObject();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Exit Confirmation");
        alert.setHeaderText(null);
        alert.setContentText(response);


        alert.showAndWait();

        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/HomePage.fxml"));
        Parent root = loader.load();
        stage.setScene(new Scene(root, 640, 480));
        stage.show();
    }

}
