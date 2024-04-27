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
import java.util.Set;

import edu.duke.ece651.shared.*;
import edu.duke.ece651.shared.dao.*;
import edu.duke.ece651.shared.model.*;
import edu.duke.ece651.userAdmin.*;

public class AddFacultyController {

  private Stage mainStage;

  @FXML
  private TextField facultyIdField;
  
  @FXML
  private TextField facultyNameField;

  @FXML
  private TextField emailField;

  public void setMainStage(Stage mainStage) {
    this.mainStage = mainStage;
  }

  @FXML
  private void addFacultyButton() {
    String facultyId = facultyIdField.getText().trim();
    String name = facultyNameField.getText().trim();
    String email = emailField.getText().trim();
    if (facultyId.isEmpty() || name.isEmpty()) {
      showAlert("Error", "Faculty ID & name & email address cannot be empty", AlertType.ERROR);
      return;
    }
    if(!Email.checkValid(email)) {
      showAlert("Error", "This email address in invalid!", AlertType.ERROR);
      return;
    }
    
    if(UserManagement.checkFacultyExistsByID(facultyId)) {
      showAlert("Error", "Faculty ID already exists", AlertType.ERROR);
        return;
    }

    if(UserManagement.checkFacultyExistsByEmail(email)) {
      showAlert("Error", "Faculty email address already exists", AlertType.ERROR);
        return;
    }
    Professor newProf = new Professor(facultyId, name, new Email(email));
    UserManagement.facultySignUp(newProf);
    if(UserManagement.checkFacultyExistsByID(facultyId)) {
      showAlert("Success", "Faculty member created successfully", AlertType.INFORMATION);
      clearForm();
    } else {
      showAlert("Error", "Failed to create the faculty member", AlertType.ERROR);
    }
  }

  private boolean addSection(String courseId, String profId) {
    SectionDAO sectionIO = new SectionDAO();
    Section newSection = new Section(1, courseId, profId);
    sectionIO.addSectionToCourse(newSection);
    return true;
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
    facultyNameField.clear();
    emailField.clear();
  }

  @FXML
  private void backToHomePage() throws Exception {
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
