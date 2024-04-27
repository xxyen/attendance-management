package edu.duke.ece651.profClient.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import edu.duke.ece651.profClient.App;
import com.fasterxml.jackson.core.type.TypeReference;

public class HomePageController {
    private Stage mainStage;

    public void setMainStage(Stage mainStage) {
        this.mainStage = mainStage;
      }
      
    @FXML
    private Label welcomeLabel;

    private ObjectMapper mapper = new ObjectMapper();

    public void setProfessorName(String name) {
        welcomeLabel.setText("Welcome, Professor " + name + "!");
    }

    @FXML
    protected void handleManipulateSections(ActionEvent event) throws IOException, ClassNotFoundException {
        HashMap<String, String> command = new HashMap<>();
        command.put("action", "ManipulateSections");
        String json = mapper.writeValueAsString(command);
        App.out.writeObject(json);
        App.out.flush();

        String jsonSections = (String) App.in.readObject();
        ObjectMapper mapper = new ObjectMapper();
        Map<String, List<String>> sectionsData = mapper.readValue(jsonSections, new TypeReference<Map<String, List<String>>>() {});
        List<String> sections = sectionsData.get("sections");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/SectionsPage.fxml"));
        Parent root = loader.load();
        SectionsPageController controller = loader.getController();
        controller.populateSections(sections);

        Stage stage = (Stage) welcomeLabel.getScene().getWindow();
        stage.setScene(new Scene(root, 640, 480));
        stage.show();
    }


    @FXML
    protected void handleLogout(ActionEvent event)  throws IOException {
        try {
            HashMap<String, String> command = new HashMap<>();
            command.put("action", "Logout");
            String json = mapper.writeValueAsString(command);
            App.out.writeObject(json);
            App.out.flush();

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
