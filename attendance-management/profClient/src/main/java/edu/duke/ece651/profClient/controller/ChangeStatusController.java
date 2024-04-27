package edu.duke.ece651.profClient.controller;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;


public class ChangeStatusController {
    @FXML
    private Label instructionLabel;

    private ObjectMapper mapper = new ObjectMapper();

    @FXML
    private ComboBox<String> studentIdComboBox;

    @FXML
    private ComboBox<String> statusComboBox;

    private List<String> studentIds;

    @FXML
    private void initialize() {
        // instructionLabel.setText("Please select a student and choose a new attendance status");
        statusComboBox.getItems().addAll("p", "a", "t");
    }

    public void setStudentIds(List<String> studentIds) {
        this.studentIds = studentIds;
        studentIdComboBox.getItems().addAll(studentIds);
    }

    @FXML
    private void handleConfirm() throws Exception{
        String selectedStudentId = studentIdComboBox.getValue();
        String selectedStatus = statusComboBox.getValue();
        System.out.println("client confirm change status:"+selectedStudentId+" "+selectedStatus);

        HashMap<String, String> command = new HashMap<>();
        command.put("studentid", selectedStudentId);
        command.put("status", selectedStatus);

        String json = mapper.writeValueAsString(command);
        App.out.writeObject(json);
        App.out.flush();

        String response = (String) App.in.readObject();
        if (response.startsWith("success")) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Update Success");
            alert.setHeaderText(null);
            alert.setContentText("Successfully update the record!");
            alert.showAndWait();
            navigateToProfessorPage();
        } else {
            showAlert("Update Error", response);
        }
    }

    private void navigateToProfessorPage() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ProfessorPage.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) instructionLabel.getScene().getWindow();
        stage.setScene(new Scene(root, 640, 480));
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