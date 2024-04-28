package edu.duke.ece651.profClient.controller;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Node;
import java.io.IOException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.duke.ece651.profClient.App;
import java.util.HashMap;


public class StartNewSessionController {
    private ObjectMapper mapper = new ObjectMapper();


    @FXML
    protected void handleTakeAttendanceTodayClick(ActionEvent event) throws IOException {
        HashMap<String, String> command = new HashMap<>();
        command.put("action", "TakeAttendanceToday");
        String json = mapper.writeValueAsString(command);
        App.out.writeObject(json);
        App.out.flush();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/TakeAttendanceTodayPage.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root, 640, 480);
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    protected void handleTakeAttendancePreviousDateClick(ActionEvent event) throws IOException {
        HashMap<String, String> command = new HashMap<>();
        command.put("action", "TakeAttendancePreviousDate");
        String json = mapper.writeValueAsString(command);
        App.out.writeObject(json);
        App.out.flush();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ChooseDatePage.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root, 640, 480);
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

}