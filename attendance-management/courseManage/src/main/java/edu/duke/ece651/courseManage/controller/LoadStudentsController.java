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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.FileHandler;
import java.util.Date;
import java.text.SimpleDateFormat;


import edu.duke.ece651.shared.*;
import edu.duke.ece651.shared.dao.*;
import edu.duke.ece651.shared.model.*;

public class LoadStudentsController {

  private Stage mainStage;

  @FXML
  private TextField sectionIdField;
  
  @FXML
  private TextField csvPathField;
  
  public void setMainStage(Stage mainStage) {
    this.mainStage = mainStage;
  }

  @FXML
  private void addStudentsButton() {
    String sectionIdString = sectionIdField.getText().trim();
    String csvPath = csvPathField.getText().trim();
    if (sectionIdString.isEmpty() || csvPath.isEmpty()) {
      showAlert("Error", "Section ID and CSV Path cannot be empty", AlertType.ERROR);
      return;
    }
    int sectionId = Integer.valueOf(sectionIdString);
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
    List<Student> students = new ArrayList<>();
    try {
      students = readStudentsFromFile(csvPath, true);
    }
    catch (Exception e) {
      showAlert("Error", e.getMessage(), AlertType.ERROR);
      return;
    }
    StudentDAO stuIO = new StudentDAO();
    Set<Student> stus = stuIO.queryAllStudents();
    /*
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
    */
    EnrollmentDAO enrollIO = new EnrollmentDAO();
    List<Enrollment> enrolls = enrollIO.listEnrollmentsBySection(sectionId);
    int added = 0;
    int enrolled = 0;
    int notexist = 0;
    for (Student student: students) {
      boolean findStu = false;
      for (Student stu : stus) {
        if (stu.getUserid().equals(student.getUserid())) {
          findStu = true;
          break;
        }
      }
      if (findStu == false) {
        notexist++;
        continue;
      }
      boolean findEnrolled = false;
      for (Enrollment enroll : enrolls) {
        if (enroll.getStudentId().equals(student.getUserid())) {
          findEnrolled = true;
          break;
        }
      }
      if (findEnrolled == true) {
        enrolled++;
        continue;
      }
      /*
    for (Enrollment enroll : enrolls) {
      if (enroll.getStudentId().equals(stuId)) {
        showAlert("Error", "Student already enrolled", AlertType.ERROR);
        return;
      }
    }
      */
      addEnrollment(sectionId, student.getUserid());
      added++;
    }
    String ans = added + " students added successfully\n" + enrolled + " students already enrolled\n" + notexist + " students ID do not exist";
    showAlert("Success", ans, AlertType.INFORMATION);
    clearForm();
  }

  private static List<Student> readStudentsFromFile(String rosterPath, boolean withHeader) throws Exception {
    File rosterFile = new File(rosterPath);
    if (!rosterFile.exists()) {
      throw new FileNotFoundException("Roster file not found at: " + rosterPath);
    }
    List<Student> students = new ArrayList<>();
    try (BufferedReader br = new BufferedReader(new FileReader(rosterFile))) {
      String line;
      if (withHeader) {
        br.readLine();
      }
      while ((line = br.readLine()) != null) {
        String[] values = line.split(",");
        Student newStudent = new Student(values[0], values[1], values[2], new Email(values[3]));
        students.add(newStudent);
      }
    }
    return students;
  }
  

  private boolean addEnrollment(int sectionId, String stuId) {
    EnrollmentDAO enrollIO = new EnrollmentDAO();
    Enrollment newEnroll = new Enrollment(sectionId, stuId, new Date(), "Enrolled", true);
    enrollIO.addEnrollment(newEnroll);
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
    csvPathField.clear();
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
