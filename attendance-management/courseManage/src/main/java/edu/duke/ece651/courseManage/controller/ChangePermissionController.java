package edu.duke.ece651.courseManage.controller;

import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// import edu.duke.ece651.courseManage.UserManagement;
import edu.duke.ece651.shared.*;
import edu.duke.ece651.shared.dao.*;
import edu.duke.ece651.shared.model.*;
import edu.duke.ece651.userAdmin.*;

public class ChangePermissionController {

  private Stage mainStage;

  @FXML
  private Label displayPermission;

  public void setMainStage(Stage mainStage) {
    this.mainStage = mainStage;
    if(UserManagement.getDisplayNamePermission()){
      displayPermission.setText("Display names of students are displayed for attendance.");
    }
    displayPermission.setText("Display names of students are NOT displayed for attendance.");
  }
    

  @FXML
  private void setPermissionToTrueButton() {
    UserManagement.setDisplayNamePermission(true);
    if(UserManagement.getDisplayNamePermission()) {
      showAlert("Success", "Show display name during taking attendance: true", AlertType.INFORMATION);
      displayPermission.setText("Display names of students are displayed for attendance.");
    } else {
      showAlert("Error", "Failed to modify it! Show display name during taking attendance: false", AlertType.INFORMATION);
    }
  }

  @FXML
  private void setPermissionToFalseButton() {
    UserManagement.setDisplayNamePermission(false);
    if(!UserManagement.getDisplayNamePermission()) {
      showAlert("Success", "Show display name during taking attendance: false", AlertType.INFORMATION);
      displayPermission.setText("Display names of students are NOT displayed for attendance.");
    } else {
      showAlert("Error", "Failed to modify it! Show display name during taking attendance: true", AlertType.INFORMATION);
    }
  }

  private void showAlert(String title, String content, AlertType type) {
    Alert alert = new Alert(type);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(content);
    alert.showAndWait();
  }


  @FXML
  private void backToHomePage() throws Exception {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/userHomePg.xml"));
    Parent homePage = loader.load();
    UserHomeController controller = loader.getController();
    controller.setMainStage(mainStage);
    // Set the new scene on the existing stage
    Scene scene = new Scene(homePage, 960, 720);
    mainStage.setScene(scene);
    mainStage.show();
  }
  
}
