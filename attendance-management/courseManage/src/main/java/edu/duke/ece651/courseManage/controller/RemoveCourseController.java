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
import javafx.scene.control.ButtonType;
import java.util.Optional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import edu.duke.ece651.shared.*;
import edu.duke.ece651.shared.dao.*;
import edu.duke.ece651.shared.model.*;

public class RemoveCourseController {

  private Stage mainStage;

  @FXML
  private TextField courseIdField;

  public void setMainStage(Stage mainStage) {
    this.mainStage = mainStage;
  }

  @FXML
  private void removeCourseButton() {
    String courseId = courseIdField.getText().trim();
    if (courseId.isEmpty()) {
      showAlert("Error", "Course ID cannot be empty", AlertType.ERROR);
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
      showAlert("Error", "Course ID does not exist", AlertType.ERROR);
      return;
    }
    if (!showConfirmationDialog("Warning", "DANGER!!! Remove a course will be destructive.\nDo you REALLY want to proceed?")) {
      return;
    }
    boolean isSuccess = removeCourse(courseId);
    if (isSuccess) {
      showAlert("Success", "Course removed successfully", AlertType.INFORMATION);
      clearForm();
    }
    else {
      showAlert("Error", "Failed to remove course", AlertType.ERROR);
    }
  }

  private boolean removeCourse(String courseId) {
    CourseDAO courseIO = new CourseDAO();
    courseIO.deleteCourse(courseId);
    return true;
  }

  private boolean showConfirmationDialog(String title, String content) {
    Alert alert = new Alert(AlertType.CONFIRMATION);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(content);
    
    Optional<ButtonType> result = alert.showAndWait();
    return result.isPresent() && result.get() == ButtonType.OK;
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
