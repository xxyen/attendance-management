package edu.duke.ece651.courseManage.controller;

import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import edu.duke.ece651.shared.*;
import edu.duke.ece651.shared.dao.*;
import edu.duke.ece651.shared.model.*;
import edu.duke.ece651.userAdmin.UserManagement;

public class RemoveStudentController {

  private Stage mainStage;

  @FXML
  private TextField studentIdField;

  public void setMainStage(Stage mainStage) {
    this.mainStage = mainStage;
  }

  @FXML
  private void removeStudentButton() {
    String studentId = studentIdField.getText().trim();
    if (studentId.isEmpty()) {
      showAlert("Error", "Student ID cannot be empty", AlertType.ERROR);
      return;
    }

    if (!UserManagement.checkStudentExistsByID(studentId)) {
      showAlert("Error", "Student ID does not exist", AlertType.ERROR);
      return;
    }
    UserManagement.removeStudent(studentId);
    if (!UserManagement.checkStudentExistsByID(studentId)) {
      showAlert("Success", "Student removed successfully", AlertType.INFORMATION);
      clearForm();
    }
    else {
      showAlert("Error", "Failed to remove student", AlertType.ERROR);
    }
  }


  private void showAlert(String title, String content, AlertType type) {
    Alert alert = new Alert(type);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(content);
    alert.showAndWait();
  }

  private void clearForm() {
    studentIdField.clear();
  }
  
  @FXML
  private void backToHome() throws Exception {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/userHomePg.xml"));
    Parent homePage = loader.load();
    UserHomeController controller = loader.getController();
    controller.setMainStage(mainStage);
    // Set the new scene on the existing stage
    Scene scene = new Scene(homePage, 960, 720);
    mainStage.setScene(scene);
    mainStage.show();
  }
  
}
