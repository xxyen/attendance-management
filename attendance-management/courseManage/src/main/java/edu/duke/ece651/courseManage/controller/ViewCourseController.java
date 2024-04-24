package edu.duke.ece651.courseManage.controller;

import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.Node;

import java.io.IOException;

public class ViewCourseController {

  private Stage mainStage;

  public void setMainStage(Stage mainStage) {
    this.mainStage = mainStage;
  }
  
  @FXML
  private void backToHome() throws Exception {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/homePg.xml"));
    Parent homePage = loader.load();
    
    // Set the new scene on the existing stage
    Scene scene = new Scene(homePage, 640, 480);
    mainStage.setScene(scene);
    mainStage.show();
  }
  
}
