package edu.duke.ece651.courseManage.controller;

import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.scene.control.ListView;
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

public class ViewProfController {

  @FXML
  private ListView<String> profListView;

  private Stage mainStage;
  private ObservableList<String> profObservableList = FXCollections.observableArrayList();

  public void setMainStage(Stage mainStage) {
    this.mainStage = mainStage;
  }

  public void initialize() {
    // Assume loadDataFromDatabase() is a method that fetches data from the database
    List<String> profData = loadDataFromDatabase(); 
    updateProfListView(profData);
  }

  private List<String> loadDataFromDatabase() {
    List<String> ans = new ArrayList<String>();
    FacultyDAO profIO = new FacultyDAO();
    Set<Professor> profs = profIO.queryAllFaculty();
    for (Professor prof: profs) {
      ans.add("ID: " + prof.getUserid() + "     Name: " + prof.getName());
    }
    return ans;
  }

  public void updateProfListView(List<String> profs) {
    profObservableList.setAll(profs);
    profListView.setItems(profObservableList);
  }
  
  @FXML
  private void backToHomePage() throws Exception {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/homePg.xml"));
    Parent homePage = loader.load();
    HomeController controller = loader.getController();
    controller.setMainStage(mainStage);
    // Set the new scene on the existing stage
    Scene scene = new Scene(homePage, 960, 720);
    mainStage.setScene(scene);
    mainStage.show();
  }
  
}
