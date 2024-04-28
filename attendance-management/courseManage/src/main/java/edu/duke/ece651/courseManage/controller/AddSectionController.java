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

public class AddSectionController {

  private Stage mainStage;

  @FXML
  private TextField courseIdField;
  
  @FXML
  private TextField profIdField;

  public void setMainStage(Stage mainStage) {
    this.mainStage = mainStage;
  }

  @FXML
  private void addSectionButton() {
    String courseId = courseIdField.getText().trim();
    String profId = profIdField.getText().trim();
    if (courseId.isEmpty() || profId.isEmpty()) {
      showAlert("Error", "Course ID and Professor ID cannot be empty", AlertType.ERROR);
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
    FacultyDAO profIO = new FacultyDAO();
    Set<Professor> profs = profIO.queryAllFaculty();
    boolean findProf = false;
    for (Professor prof : profs) {
      if (prof.getUserid().equals(profId)) {
        findProf = true;
        break;
      }
    }
    if (findProf == false) {
      showAlert("Error", "Professor ID does not exist", AlertType.ERROR);
      return;
    }
    boolean isSuccess = addSection(courseId, profId);
    if (isSuccess) {
      showAlert("Success", "Section added successfully", AlertType.INFORMATION);
      clearForm();
    }
    else {
      showAlert("Error", "Failed to add section", AlertType.ERROR);
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
    courseIdField.clear();
    profIdField.clear();
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
