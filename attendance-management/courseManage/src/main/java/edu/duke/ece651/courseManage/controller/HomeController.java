package edu.duke.ece651.courseManage.controller;

import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.Node;

import java.io.IOException;

public class HomeController {
  
  private Stage mainStage;
  
  public void setMainStage(Stage mainStage) {
    this.mainStage = mainStage;
  }
  
  @FXML
  private void courseManage() throws Exception {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/courseHomePg.xml"));
    Parent coursesPage = loader.load();
    // Set the controller for the view course page
    CourseHomeController controller = loader.getController();
    controller.setMainStage(mainStage);
    // Set the new scene on the existing stage
    Scene scene = new Scene(coursesPage, 960, 720);
    mainStage.setScene(scene);
    mainStage.show();
  }
  
  @FXML
  private void userManage() throws Exception {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/userHomePg.xml"));
    Parent addCoursePage = loader.load();
    // Set the controller for the view course page
    UserHomeController controller = loader.getController();
    controller.setMainStage(mainStage);
    // Set the new scene on the existing stage
    Scene scene = new Scene(addCoursePage, 960, 720);
    mainStage.setScene(scene);
    mainStage.show();
  }
  

  @FXML
  private void exitSystem() throws Exception {
    System.exit(0);
  }
  
}
