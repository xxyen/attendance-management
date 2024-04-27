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

public class SectionsPageController {
    @FXML
    private VBox sectionsContainer;

    private ToggleGroup group = new ToggleGroup();

    public void populateSections(List<String> sections) {
        int index = 0;
        for (String sec : sections) {
            RadioButton rb = new RadioButton(sec);
            rb.setToggleGroup(group);
            rb.setUserData(index);
            sectionsContainer.getChildren().add(rb);
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
            System.out.println("Selected Section Index: " + selectedIndex);
            sendSelectedIndexToServer(selectedIndex);
            try {
                navigateToProfessorPage();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void sendSelectedIndexToServer(int index) {
        try {
            HashMap<String, Object> command = new HashMap<>();
            command.put("action", "SelectSection");
            command.put("index", index);
            String json = new ObjectMapper().writeValueAsString(command);
            App.out.writeObject(json);
            App.out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void navigateToProfessorPage() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ProfessorPage.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) sectionsContainer.getScene().getWindow();
        stage.setScene(new Scene(root, 640, 480));
        stage.show();
    }
}
