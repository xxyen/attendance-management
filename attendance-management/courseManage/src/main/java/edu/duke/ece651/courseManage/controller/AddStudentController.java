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
import edu.duke.ece651.userAdmin.*;

public class AddStudentController {

  private Stage mainStage;

  @FXML
  private TextField studentIdField;
  
  @FXML
  private TextField legalNameField;

  @FXML
  private TextField displayNameField;

  @FXML
  private TextField emailField;
  

  public void setMainStage(Stage mainStage) {
    this.mainStage = mainStage;
  }

  @FXML
  private void createStudentButtonWithDName() {
    String studentId = studentIdField.getText().trim();
    String legalName = legalNameField.getText().trim();
    String displayName = displayNameField.getText().trim();
    String email = emailField.getText().trim();
    if (studentId.isEmpty() || legalName.isEmpty() || email.isEmpty()) {
      showAlert("Error", "Student ID & name & email address cannot be empty", AlertType.ERROR);
      return;
    }

    if(!Email.checkValid(email)) {
      showAlert("Error", "This email address in invalid!", AlertType.ERROR);
      return;
    }

    if(UserManagement.checkStudentExistsByID(studentId)) {
      showAlert("Error", "Student ID already exists", AlertType.ERROR);
        return;
    }

    if(UserManagement.checkStudentExistsByEmail(email)) {
      showAlert("Error", "Student email address already exists", AlertType.ERROR);
        return;
    }

    Student newStu = new Student(studentId, legalName, legalName, new Email(email));
    if(!displayName.isEmpty()) {
      newStu.setDisplayName(displayName);
    }
    UserManagement.studentSignUp(newStu);
    if (UserManagement.checkStudentExistsByID(studentId)) {
      showAlert("Success", "Student created successfully", AlertType.INFORMATION);
      clearForm();
    }
    else {
      showAlert("Error", "Failed to create the student", AlertType.ERROR);
    }
  }

  @FXML
  private void createStudentButtonWithoutDName() {
    String studentId = studentIdField.getText().trim();
    String legalName = legalNameField.getText().trim();
    String email = emailField.getText().trim();
    if (studentId.isEmpty() || legalName.isEmpty() || email.isEmpty()) {
      showAlert("Error", "Student ID & name & email address cannot be empty", AlertType.ERROR);
      return;
    }

    if(!Email.checkValid(email)) {
      showAlert("Error", "This email address in invalid!", AlertType.ERROR);
      return;
    }

    if(UserManagement.checkStudentExistsByID(studentId)) {
      showAlert("Error", "Student ID already exists", AlertType.ERROR);
        return;
    }

    if(UserManagement.checkStudentExistsByEmail(email)) {
      showAlert("Error", "Student email address already exists", AlertType.ERROR);
        return;
    }

    Student newStu = new Student(studentId, legalName, legalName, new Email(email));
    UserManagement.studentSignUp(newStu);
    if (UserManagement.checkStudentExistsByID(studentId)) {
      showAlert("Success", "Student created successfully", AlertType.INFORMATION);
      clearForm();
    }
    else {
      showAlert("Error", "Failed to create the student", AlertType.ERROR);
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
    legalNameField.clear();
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
