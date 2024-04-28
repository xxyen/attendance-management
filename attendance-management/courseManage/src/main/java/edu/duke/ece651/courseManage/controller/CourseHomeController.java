package edu.duke.ece651.courseManage.controller;

import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.Node;

import java.io.IOException;

public class CourseHomeController {
  
  private Stage mainStage;
  
  public void setMainStage(Stage mainStage) {
    this.mainStage = mainStage;
  }
  
  @FXML
  private void viewCourses() throws Exception {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/viewCoursePg.xml"));
    Parent coursesPage = loader.load();
    // Set the controller for the view course page
    ViewCourseController controller = loader.getController();
    controller.setMainStage(mainStage);
    // Set the new scene on the existing stage
    Scene scene = new Scene(coursesPage, 960, 720);
    mainStage.setScene(scene);
    mainStage.show();
  }

  @FXML
  private void addCourse() throws Exception {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/addCoursePg.xml"));
    Parent addCoursePage = loader.load();
    // Set the controller for the view course page
    AddCourseController controller = loader.getController();
    controller.setMainStage(mainStage);
    // Set the new scene on the existing stage
    Scene scene = new Scene(addCoursePage, 960, 720);
    mainStage.setScene(scene);
    mainStage.show();
  }

  @FXML
  private void removeCourse() throws Exception {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/removeCoursePg.xml"));
    Parent removeCoursePage = loader.load();
    // Set the controller for the view course page
    RemoveCourseController controller = loader.getController();
    controller.setMainStage(mainStage);
    // Set the new scene on the existing stage
    Scene scene = new Scene(removeCoursePage, 960, 720);
    mainStage.setScene(scene);
    mainStage.show();
  }

  @FXML
  private void updateCourse() throws Exception {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/updatePg.xml"));
    Parent updateCoursePage = loader.load();
    // Set the controller for the view course page
    UpdateController controller = loader.getController();
    controller.setMainStage(mainStage);
    // Set the new scene on the existing stage
    Scene scene = new Scene(updateCoursePage, 960, 720);
    mainStage.setScene(scene);
    mainStage.show();
  }

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
