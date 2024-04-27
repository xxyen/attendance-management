package edu.duke.ece651.profClient.controller;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.Alert;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import java.io.File;
import java.util.HashMap;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.duke.ece651.profClient.App;
import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Arrays;
import java.nio.charset.StandardCharsets;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;



public class ExportRecordController implements Initializable {
    @FXML private ComboBox<String> formatComboBox;
    @FXML private Label selectedDirectoryLabel;
    private File selectedDirectory;
    private ObjectMapper mapper = new ObjectMapper();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        formatComboBox.getItems().addAll(Arrays.asList("json", "xml"));
    }

    @FXML
    protected void handleDirectoryChoice() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Select Directory");
        Stage stage = (Stage) formatComboBox.getScene().getWindow();
        selectedDirectory = directoryChooser.showDialog(stage);
        if (selectedDirectory != null) {
            selectedDirectoryLabel.setText(selectedDirectory.getAbsolutePath());
        }
    }

    @FXML
    protected void handleConfirm() {
        System.out.println("format:"+formatComboBox.getValue()+" path:"+selectedDirectory.getAbsolutePath());
        if (formatComboBox.getValue() != null && selectedDirectory != null) {
            try {
                HashMap<String, String> command = new HashMap<>();
                command.put("format", formatComboBox.getValue());
                command.put("path", selectedDirectory.getAbsolutePath());
                String json = mapper.writeValueAsString(command);
                App.out.writeObject(json);
                App.out.flush();

                receiveFileFromServer();
                
            } catch (Exception e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Export Error");
                alert.setHeaderText(null);
                alert.setContentText("Failed to export: " + e.getMessage());
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Export Error");
            alert.setHeaderText(null);
            alert.setContentText("Please select both a format and a directory.");
            alert.showAndWait();
        }
    }

    private void receiveFileFromServer() throws IOException, ClassNotFoundException {
        String fileName = (String) App.in.readObject();
        // fileName = fileName.replace("'", "");
        File file = new File(selectedDirectory, fileName);
        FileOutputStream fileOut = new FileOutputStream(file);
    
        InputStream rawIn = App.socket.getInputStream();  
        byte[] buffer = new byte[4096];
        int bytesRead;
        while ((bytesRead = rawIn.read(buffer)) != -1) {
            String str = new String(buffer, 0, bytesRead, StandardCharsets.UTF_8);
            if(str.contains("EOF")) {
                break;
            }
            fileOut.write(buffer, 0, bytesRead);
        }
        fileOut.close();
        
        // while ((bytesRead = rawIn.read(buffer)) != -1) {
        //     fileOut.write(buffer, 0, bytesRead);
        // }
        // fileOut.close();
    
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Export Confirmation");
        alert.setHeaderText(null);
        alert.setContentText("File '" + fileName + "' has been successfully exported and saved.");
        alert.showAndWait();

        navigateBackToProfessorPage();
    }
    private void navigateBackToProfessorPage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ProfessorPage.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root, 640, 480);
            Stage stage = (Stage) formatComboBox.getScene().getWindow(); 
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Navigation Error");
            errorAlert.setHeaderText(null);
            errorAlert.setContentText("Failed to return to the main page: " + e.getMessage());
            errorAlert.showAndWait();
        }
    }
    
}
