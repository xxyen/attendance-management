package edu.duke.ece651.courseManage.controller;

import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.scene.Node;

import java.io.IOException;

import edu.duke.ece651.userAdmin.*;
import edu.duke.ece651.shared.*;

public class UpdateFacultyController {
  
  @FXML
  private TextField facultyIdField;

  @FXML
  private TextField emailField;
  
  private Stage mainStage;
  public void setMainStage(Stage mainStage) {
    this.mainStage = mainStage;
  }

  @FXML
  private void updateFacultyButton() {
    String facultyId = facultyIdField.getText().trim();
    String email = emailField.getText().trim();
    if (facultyId.isEmpty()) {
      showAlert("Error", "Faculty ID cannot be empty", AlertType.ERROR);
      return;
    }

    if(!UserManagement.checkFacultyExistsByID(facultyId)) {
      showAlert("Error", "Faculty ID does not exist", AlertType.ERROR);
        return;
    }

    if(!email.isEmpty() && !Email.checkValid(email)) {
      showAlert("Error", "This email address in invalid!", AlertType.ERROR);
      return;
    }

    
    Professor fac = UserManagement.getFacultyByID(facultyId);
    if(UserManagement.checkFacultyExistsByEmail(email) && !fac.getEmail().getEmailAddr().equals(email)) {
      showAlert("Error", "Faculty email address already exists", AlertType.ERROR);
        return;
    }

    
    if(!email.isEmpty()) {
      try {
        fac.setEmail(email);
      } catch(Exception e) {}
    }
    UserManagement.updateFaculty(fac);
    showAlert("Success", "Faculty created successfully", AlertType.INFORMATION);
    clearForm();
  }

  private void showAlert(String title, String content, AlertType type) {
    Alert alert = new Alert(type);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(content);
    alert.showAndWait();
  }

  private void clearForm() {
    facultyIdField.clear();
    emailField.clear();
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
