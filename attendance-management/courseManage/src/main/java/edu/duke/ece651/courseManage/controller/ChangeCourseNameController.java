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

public class ChangeCourseNameController {

  private Stage mainStage;

  @FXML
  private TextField courseIdField;
  
  @FXML
  private TextField courseNameField;

  public void setMainStage(Stage mainStage) {
    this.mainStage = mainStage;
  }

  @FXML
  private void changeCourseNameButton() {
    String courseId = courseIdField.getText().trim();
    String newName = courseNameField.getText().trim();
    if (courseId.isEmpty() || newName.isEmpty()) {
      showAlert("Error", "Course ID and new name cannot be empty", AlertType.ERROR);
      return;
    }
    CourseDAO courseIO = new CourseDAO();
    List<Course> courses = courseIO.findAllCourses();
    boolean findCourse = false;
    for (Course course: courses) {
      if (course.getCourseId().equals(courseId)) {
        findCourse = true;
        break;
      }
    }
    if (findCourse == false) {
      showAlert("Error", "Course ID already exists", AlertType.ERROR);
      return;
    }
    boolean isSuccess = changeCourseName(courseId, newName);
    if (isSuccess) {
      showAlert("Success", "Course name changed successfully", AlertType.INFORMATION);
      clearForm();
    }
    else {
      showAlert("Error", "Failed to change course name", AlertType.ERROR);
    }
  }

  private boolean changeCourseName(String courseId, String newName) {
    CourseDAO courseIO = new CourseDAO();
    courseIO.updateCourseName(courseId, newName);
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
    courseIdField.clear();
    courseNameField.clear();
  }

  @FXML
  private void backToUpdatePage() throws Exception {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/updatePg.xml"));
    Parent updatePage = loader.load();
    UpdateController controller = loader.getController();
    controller.setMainStage(mainStage);
    // Set the new scene on the existing stage
    Scene scene = new Scene(updatePage, 960, 720);
    mainStage.setScene(scene);
    mainStage.show();
  }
  
}
