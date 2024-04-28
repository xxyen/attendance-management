package edu.duke.ece651.courseManage.controller;

import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.Node;

import java.io.IOException;

import edu.duke.ece651.userAdmin.*;

public class UserHomeController {
  
  private Stage mainStage;
  
  public void setMainStage(Stage mainStage) {
    this.mainStage = mainStage;
  }
  
  @FXML
  private void viewStudents() throws Exception {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/viewStuPgUser.xml"));
    Parent studentsPage = loader.load();
    ViewStuUserController controller = loader.getController();
    controller.setMainStage(mainStage);
    Scene scene = new Scene(studentsPage, 960, 720);
    mainStage.setScene(scene);
    mainStage.show();
  }

  @FXML
  private void viewFaculty() throws Exception {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/viewProfPgUser.xml"));
    Parent facultyPage = loader.load();
    ViewProfUserController controller = loader.getController();
    controller.setMainStage(mainStage);
    Scene scene = new Scene(facultyPage, 960, 720);
    mainStage.setScene(scene);
    mainStage.show();
  }

  @FXML
  private void addStudent() throws Exception {
    FXMLLoader loader = null;
    if(UserManagement.getDisplayNamePermission()) {
      loader = new FXMLLoader(getClass().getResource("/ui/addStudentPgWithDisplayName.xml"));
    } else {
      loader = new FXMLLoader(getClass().getResource("/ui/addStudentPgWithoutDisplayName.xml"));
    }
    Parent addStudentPage = loader.load();
    AddStudentController controller = loader.getController();
    controller.setMainStage(mainStage);
    Scene scene = new Scene(addStudentPage, 960, 720);
    mainStage.setScene(scene);
    mainStage.show();
  }

  @FXML
  private void updateStudent() throws Exception {
    FXMLLoader loader = null;
    if(UserManagement.getDisplayNamePermission()) {
      loader = new FXMLLoader(getClass().getResource("/ui/updateStudentPgWithDisplayName.xml"));
    } else {
      loader = new FXMLLoader(getClass().getResource("/ui/updateStudentPgWithoutDisplayName.xml"));
    }
    
    Parent updateStudentPage = loader.load();
    UpdateStudentController controller = loader.getController();
    controller.setMainStage(mainStage);
    Scene scene = new Scene(updateStudentPage, 960, 720);
    mainStage.setScene(scene);
    mainStage.show();
  }

  @FXML
  private void addFaculty() throws Exception {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/addFacultyPg.xml"));
    Parent addFacultyPage = loader.load();
    AddFacultyController controller = loader.getController();
    controller.setMainStage(mainStage);
    Scene scene = new Scene(addFacultyPage, 960, 720);
    mainStage.setScene(scene);
    mainStage.show();
  }

  @FXML
  private void updateFaculty() throws Exception {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/updateFacultyPg.xml"));
    Parent updateFacultyPage = loader.load();
    UpdateFacultyController controller = loader.getController();
    controller.setMainStage(mainStage);
    Scene scene = new Scene(updateFacultyPage, 960, 720);
    mainStage.setScene(scene);
    mainStage.show();
  }

  @FXML
  private void removeStudent() throws Exception {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/removeStudentPg.xml"));
    Parent removeStudentPage = loader.load();
    RemoveStudentController controller = loader.getController();
    controller.setMainStage(mainStage);
    Scene scene = new Scene(removeStudentPage, 960, 720);
    mainStage.setScene(scene);
    mainStage.show();
  }

  @FXML
  private void removeFaculty() throws Exception {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/removeFacultyPg.xml"));
    Parent removeFacultyPage = loader.load();
    RemoveFacultyController controller = loader.getController();
    controller.setMainStage(mainStage);
    Scene scene = new Scene(removeFacultyPage, 960, 720);
    mainStage.setScene(scene);
    mainStage.show();
  }

  @FXML
  private void setPermission() throws Exception {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/changePermissionPg.xml"));
    Parent setPermissionPage = loader.load();
    ChangePermissionController controller = loader.getController();
    controller.setMainStage(mainStage);
    Scene scene = new Scene(setPermissionPage, 960, 720);
    mainStage.setScene(scene);
    mainStage.show();
  }

  // @FXML
  // private void updateCourse() throws Exception {
  //   FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/updatePg.xml"));
  //   Parent updateCoursePage = loader.load();
  //   // Set the controller for the view course page
  //   UpdateController controller = loader.getController();
  //   controller.setMainStage(mainStage);
  //   // Set the new scene on the existing stage
  //   Scene scene = new Scene(updateCoursePage, 960, 720);
  //   mainStage.setScene(scene);
  //   mainStage.show();
  // }

  @FXML
  private void exitSystem() throws Exception {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/homePg.xml"));
    Parent updateCoursePage = loader.load();
    // Set the controller for the view course page
    HomeController controller = loader.getController();
    controller.setMainStage(mainStage);
    // Set the new scene on the existing stage
    Scene scene = new Scene(updateCoursePage, 960, 720);
    mainStage.setScene(scene);
    mainStage.show();
  }
  
}
