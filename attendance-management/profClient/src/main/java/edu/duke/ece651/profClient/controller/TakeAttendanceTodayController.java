package edu.duke.ece651.profClient.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import edu.duke.ece651.profClient.App;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Parent;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TakeAttendanceTodayController {

    @FXML
    private VBox attendanceVBox;

    private ObjectMapper mapper = new ObjectMapper();

    @FXML
    private void initialize() {
        try {
            String jsonStudentIds = (String) App.in.readObject();
            List<String> studentIds = mapper.readValue(jsonStudentIds, List.class);

            for (String studentId : studentIds) {
                HBox hBox = new HBox(10);
                Label studentIdLabel = new Label(studentId);
                studentIdLabel.setPrefWidth(200);
                hBox.getChildren().add(studentIdLabel);

                ToggleGroup toggleGroup = new ToggleGroup();

                RadioButton presentRadioButton = new RadioButton("p");
                presentRadioButton.setToggleGroup(toggleGroup);
                RadioButton absentRadioButton = new RadioButton("a");
                absentRadioButton.setToggleGroup(toggleGroup);
                absentRadioButton.setSelected(true);
                RadioButton tardyRadioButton = new RadioButton("t");
                tardyRadioButton.setToggleGroup(toggleGroup);

                HBox statusBox = new HBox(10);
                statusBox.getChildren().addAll(presentRadioButton, absentRadioButton, tardyRadioButton);
                statusBox.setPrefWidth(200);
                hBox.getChildren().add(statusBox);

                attendanceVBox.getChildren().add(hBox);
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleConfirmButtonClick() {
        try {
            HashMap<String, String> attendanceData = new HashMap<>();
            for (Node node : attendanceVBox.getChildren()) {
                if (node instanceof HBox) {
                    HBox hBox = (HBox) node;
                    Label studentIdLabel = (Label) hBox.getChildren().get(0);
                    String studentId = studentIdLabel.getText();
                    HBox statusBox = (HBox) hBox.getChildren().get(1);
                    RadioButton selectedButton = (RadioButton) statusBox.getChildren().stream()
                            .filter(RadioButton.class::isInstance)
                            .map(RadioButton.class::cast)
                            .filter(RadioButton::isSelected)
                            .findFirst()
                            .orElse(null);
                    if (selectedButton != null) {
                        String status = selectedButton.getText().toLowerCase().substring(0, 1);
                        attendanceData.put(studentId, status);
                    }
                }
            }

            String json = mapper.writeValueAsString(attendanceData);
            App.out.writeObject(json);
            App.out.flush();

            showAlert("Successfully take attendance for today!");
            navigateToProfessorPage();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Attendance Confirmation");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void navigateToProfessorPage() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ProfessorPage.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) attendanceVBox.getScene().getWindow();
        stage.setScene(new Scene(root, 640, 480));
        stage.show();
    }
}