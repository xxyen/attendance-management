package edu.duke.ece651.profClient.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import java.util.List;
import java.util.HashMap;
import edu.duke.ece651.profClient.App;
import java.io.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Parent;
import com.fasterxml.jackson.core.type.TypeReference;


public class SessionListController {
    @FXML
    private VBox sessionsContainer;

    private ToggleGroup group = new ToggleGroup();

    public void populateSessions(List<String> sessions) {
        int index = 0;
        for (String sessionDate : sessions) {
            RadioButton rb = new RadioButton(sessionDate);
            rb.setToggleGroup(group);
            rb.setUserData(index);
            sessionsContainer.getChildren().add(rb);
            if (index == 0) {
                rb.setSelected(true);
            }
            index++;
        }
    }

    @FXML
    protected void handleConfirm() {
        RadioButton selected = (RadioButton) group.getSelectedToggle();
        if (selected != null) {
            int selectedIndex = (Integer) selected.getUserData();
            System.out.println("Selected Session Index: " + selectedIndex);
            sendSelectedIndexToServer(selectedIndex);
            try {
                navigateToChangeStatusPage();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void sendSelectedIndexToServer(int index) {
        try {
            HashMap<String, Object> command = new HashMap<>();
            command.put("action", "SelectSession");
            command.put("index", index);
            String json = new ObjectMapper().writeValueAsString(command);
            App.out.writeObject(json);
            App.out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void navigateToChangeStatusPage() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ChangeStatusPage.fxml"));
        Parent root = loader.load();
        ChangeStatusController controller = loader.getController();
    
        try {
            String studentIdsJson = (String) App.in.readObject();
            List<String> studentIds = new ObjectMapper().readValue(studentIdsJson, new TypeReference<List<String>>(){});

            controller.setStudentIds(studentIds);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    
        Stage stage = (Stage) sessionsContainer.getScene().getWindow();
        stage.setScene(new Scene(root, 640, 480));
        stage.show();
    }
}
