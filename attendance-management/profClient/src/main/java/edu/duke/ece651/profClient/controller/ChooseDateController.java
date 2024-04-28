package edu.duke.ece651.profClient.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Node;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import edu.duke.ece651.profClient.App;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;


public class ChooseDateController {

    @FXML
    private DatePicker datePicker;

    private ObjectMapper mapper = new ObjectMapper();


    @FXML
    private void initialize() {
        datePicker.setValue(LocalDate.now());
    }

    @FXML
    private void handleConfirmButtonClick(javafx.event.ActionEvent event) throws IOException {
        LocalDate selectedDate = datePicker.getValue();
        if (selectedDate.isAfter(LocalDate.now())) {
            showAlert("You cannot choose a future date.");
            return;
        }

        String formattedDate = selectedDate.format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
        showAlert("You have successfully chosen date " + formattedDate);

        HashMap<String, String> date = new HashMap<>();
        date.put("date", getSelectedDate());
        String jsonDate = mapper.writeValueAsString(date);
        App.out.writeObject(jsonDate);
        App.out.flush();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/TakeAttendancePreviousDatePage.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root, 640, 480);
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }


    private String getSelectedDate() {
        LocalDate date = datePicker.getValue();
        return date.format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Date Selection");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}