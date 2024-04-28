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
import java.util.List;
import java.util.ArrayList;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.duke.ece651.shared.model.*;
import com.fasterxml.jackson.core.type.TypeReference;
import java.text.SimpleDateFormat;


public class ProfessorPageController {    
    private ObjectMapper mapper = new ObjectMapper();

    @FXML
    protected void handleStartNewSession(ActionEvent event) throws IOException {
        HashMap<String, String> command = new HashMap<>();
        command.put("action", "TakeAttendance");
        String json = mapper.writeValueAsString(command);
        App.out.writeObject(json);
        App.out.flush();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/StartNewSessionPage.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root, 640, 480);
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    protected void handleChangeAttendance(ActionEvent event) throws IOException {
        HashMap<String, String> command = new HashMap<>();
        command.put("action", "ChangeStatus");
        String json = mapper.writeValueAsString(command);
        App.out.writeObject(json);
        App.out.flush();

        try {
            String sessionJson = (String) App.in.readObject();
            List<Session> sessionList = mapper.readValue(sessionJson, new TypeReference<List<Session>>(){});
            List<String> sessionDates = new ArrayList<>();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
            for (Session session : sessionList) {
                String dateString = dateFormat.format(session.getSessionDate());
                String startTimeString = timeFormat.format(session.getStartTime());
                String dateTimeString = dateString + " " + startTimeString;
                sessionDates.add(dateTimeString);
            }

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/SessionListPage.fxml"));
            Parent root = loader.load();
            SessionListController controller = loader.getController();
            controller.populateSessions(sessionDates);
            Scene scene = new Scene(root, 640, 480);
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
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
