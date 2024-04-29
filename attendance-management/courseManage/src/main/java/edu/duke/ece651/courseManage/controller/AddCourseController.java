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

public class AddCourseController {

  private Stage mainStage;

  @FXML
  private TextField courseIdField;
  
  @FXML
  private TextField courseNameField;

  public void setMainStage(Stage mainStage) {
    this.mainStage = mainStage;
  }

  public void setFields(TextField IdField, TextField NameField) {
    this.courseIdField = IdField;
    this.courseNameField = NameField;
  }

  @FXML
  private void createCourseButton() {
    String courseId = courseIdField.getText().trim();
    String courseName = courseNameField.getText().trim();
    if (courseId.isEmpty() || courseName.isEmpty()) {
      showAlert("Error", "Course ID and name cannot be empty", AlertType.ERROR);
      return;
    }
    CourseDAO courseIO = new CourseDAO();
    List<Course> courses = courseIO.findAllCourses();
    for (Course course: courses) {
      if (course.getCourseId().equals(courseId)) {
        showAlert("Error", "Course ID already exists", AlertType.ERROR);
        return;
      }
    }
    boolean isSuccess = createCourse(courseId, courseName);
    if (isSuccess) {
      showAlert("Success", "Course created successfully", AlertType.INFORMATION);
      clearForm();
    }
    else {
      showAlert("Error", "Failed to create course", AlertType.ERROR);
    }
  }

  private boolean createCourse(String courseId, String courseName) {
    CourseDAO courseIO = new CourseDAO();
    Course newCourse = new Course(courseId, courseName);
    courseIO.addCourse(newCourse);
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
  private void backToHome() throws Exception {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/courseHomePg.xml"));
    Parent homePage = loader.load();
    CourseHomeController controller = loader.getController();
    controller.setMainStage(mainStage);
    // Set the new scene on the existing stage
    Scene scene = new Scene(homePage, 960, 720);
    mainStage.setScene(scene);
    mainStage.show();
  }
  
}
