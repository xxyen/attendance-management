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

import edu.duke.ece651.shared.*;
import edu.duke.ece651.shared.dao.*;
import edu.duke.ece651.shared.model.*;

public class ViewSectionController {

  @FXML
  private ListView<String> sectionListView;

  private Stage mainStage;
  private ObservableList<String> sectionObservableList = FXCollections.observableArrayList();

  public void setMainStage(Stage mainStage) {
    this.mainStage = mainStage;
  }

  public void initialize() {
    // Assume loadDataFromDatabase() is a method that fetches data from the database
    List<String> sectionData = loadDataFromDatabase(); 
    updateSectionListView(sectionData);
  }

  private List<String> loadDataFromDatabase() {
    List<String> ans = new ArrayList<String>();
    SectionDAO sectionIO = new SectionDAO();
    List<Section> sections = sectionIO.queryAllSections();
    for (Section section: sections) {
      ans.add(section.toString());
    }
    return ans;
  }

  public void updateSectionListView(List<String> sections) {
    sectionObservableList.setAll(sections);
    sectionListView.setItems(sectionObservableList);
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
