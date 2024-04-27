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

public class UpdateStudentController {
  
  @FXML
  private TextField studentIdField;
  
  // @FXML
  // private TextField legalNameField;

  @FXML
  private TextField displayNameField;

  @FXML
  private TextField emailField;
  
  private Stage mainStage;
  public void setMainStage(Stage mainStage) {
    this.mainStage = mainStage;
  }

  @FXML
  private void updateStudentButtonWithDName() {
    String studentId = studentIdField.getText().trim();
    // String legalName = legalNameField.getText().trim();
    String displayName = displayNameField.getText().trim();
    String email = emailField.getText().trim();
    if (studentId.isEmpty()) {
      showAlert("Error", "Student ID cannot be empty", AlertType.ERROR);
      return;
    }

    if(!UserManagement.checkStudentExistsByID(studentId)) {
      showAlert("Error", "Student ID does not exist", AlertType.ERROR);
        return;
    }

    if(!email.isEmpty() && !Email.checkValid(email)) {
      showAlert("Error", "This email address in invalid!", AlertType.ERROR);
      return;
    }

    
    Student stu = UserManagement.getStudentByID(studentId);
    if(UserManagement.checkStudentExistsByEmail(email) && !stu.getEmail().getEmailAddr().equals(email)) {
      showAlert("Error", "Student email address already exists", AlertType.ERROR);
        return;
    }

    if(!displayName.isEmpty()) {
      stu.setDisplayName(displayName);
    }
    if(!email.isEmpty()) {
      try {
        stu.setEmail(email);
      } catch(Exception e) {}
    }
    UserManagement.updateStudent(stu);
    showAlert("Success", "Student created successfully", AlertType.INFORMATION);
    studentIdField.clear();
    emailField.clear();
  }

  @FXML
  private void updateStudentButtonWithoutDName() {
    String studentId = studentIdField.getText().trim();
    // String legalName = legalNameField.getText().trim();
    String email = emailField.getText().trim();
    if (studentId.isEmpty()) {
      showAlert("Error", "Student ID cannot be empty", AlertType.ERROR);
      return;
    }

    if(!UserManagement.checkStudentExistsByID(studentId)) {
      showAlert("Error", "Student ID does not exist", AlertType.ERROR);
        return;
    }

    if(!email.isEmpty() && !Email.checkValid(email)) {
      showAlert("Error", "This email address in invalid!", AlertType.ERROR);
      return;
    }

    
    Student stu = UserManagement.getStudentByID(studentId);
    if(UserManagement.checkStudentExistsByEmail(email) && !stu.getEmail().getEmailAddr().equals(email)) {
      showAlert("Error", "Student email address already exists", AlertType.ERROR);
        return;
    }
    if(!email.isEmpty()) {
      try {
        stu.setEmail(email);
      } catch(Exception e) {}
    }
    UserManagement.updateStudent(stu);
    showAlert("Success", "Student created successfully", AlertType.INFORMATION);
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
    studentIdField.clear();
    displayNameField.clear();
    emailField.clear();
  }

  
  @FXML
  private void backToHome() throws Exception {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/homePg.xml"));
    Parent homePage = loader.load();
    HomeController controller = loader.getController();
    controller.setMainStage(mainStage);
    // Set the new scene on the existing stage
    Scene scene = new Scene(homePage, 960, 720);
    mainStage.setScene(scene);
    mainStage.show();
  }
  
}
