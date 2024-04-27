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

public class RemoveEnrollmentController {

  private Stage mainStage;

  @FXML
  private TextField sectionIdField;

  @FXML
  private TextField studentIdField;

  public void setMainStage(Stage mainStage) {
    this.mainStage = mainStage;
  }

  @FXML
  private void removeEnrollmentButton() {
    String sectionIdString = sectionIdField.getText().trim();
    String stuId = studentIdField.getText().trim();
    if (sectionIdString.isEmpty() || stuId.isEmpty()) {
      showAlert("Error", "Section ID and Student ID cannot be empty", AlertType.ERROR);
      return;
    }
    int sectionId = Integer.valueOf(sectionIdField.getText().trim());
    SectionDAO sectionIO = new SectionDAO();
    List<Section> sections = sectionIO.queryAllSections();
    boolean findSection = false;
    for (Section section: sections) {
      if (section.getSectionId() == sectionId) {
        findSection = true;
        break;
      }
    }
    if (findSection == false) {
      showAlert("Error", "Section ID does not exist", AlertType.ERROR);
      return;
    }
    StudentDAO stuIO = new StudentDAO();
    Set<Student> stus = stuIO.queryAllStudents();
    boolean findStu = false;
    for (Student stu : stus) {
      if (stu.getUserid().equals(stuId)) {
        findStu = true;
        break;
      }
    }
    if (findStu == false) {
      showAlert("Error", "Student ID does not exist", AlertType.ERROR);
      return;
    }
    EnrollmentDAO enrollIO = new EnrollmentDAO();
    List<Enrollment> enrolls = enrollIO.listEnrollmentsBySection(sectionId);
    for (Enrollment enro : enrolls) {
      if (enro.getStudentId().equals(stuId)) {
        removeEnrollment(enro.getEnrollmentId());
        showAlert("Success", "Enrollment removed successfully", AlertType.INFORMATION);
        clearForm();
        return;
      }
    }
    showAlert("Error", "Student is not enrolled", AlertType.ERROR);
  }

  private boolean removeEnrollment(int enrollId) {
    EnrollmentDAO enrollIO = new EnrollmentDAO();
    enrollIO.deleteEnrollment(enrollId);
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
    sectionIdField.clear();
    studentIdField.clear();
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
