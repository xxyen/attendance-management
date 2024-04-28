package edu.duke.ece651.courseManage.controller;

import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import edu.duke.ece651.shared.*;
import edu.duke.ece651.shared.dao.*;
import edu.duke.ece651.shared.model.*;
import edu.duke.ece651.userAdmin.*;

public class ViewStuUserController {

  @FXML
  private ListView<String> stuListView;

  @FXML
  private Label displayPermission;

  private Stage mainStage;
  private ObservableList<String> stuObservableList = FXCollections.observableArrayList();

  public void setMainStage(Stage mainStage) {
    this.mainStage = mainStage;
  }

  public void initialize() {
    // Assume loadDataFromDatabase() is a method that fetches data from the database
    Set<Student> allStu = UserManagement.getAllStudent();
    List<String> stuList = new ArrayList<>();
    if(UserManagement.getDisplayNamePermission()){
      for(Student stu: allStu) {
        stuList.add("ID: " + stu.getUserid() + ",  Legal Name: " + stu.getLegalName() + ",  Display Name: " + stu.getDisplayName() + ",  " + stu.getEmail().getEmailAddr());
      }
      displayPermission.setText("Students are allowed to have their display names.");
    } else {
      for(Student stu: allStu) {
        //stuList.add("ID: " + stu.getUserid() + ",  Legal Name: " + stu.getLegalName() + stu.getEmail().getEmailAddr());
        stuList.add(stu.toString());
      }
      displayPermission.setText("Students are not allowed to have their display names.");
    }
    updateStuListView(stuList);
  }

  public void updateStuListView(List<String> stus) {
    stuObservableList.setAll(stus);
    stuListView.setItems(stuObservableList);
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
