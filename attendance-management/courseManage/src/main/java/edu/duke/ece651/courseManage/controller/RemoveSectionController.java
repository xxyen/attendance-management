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

import edu.duke.ece651.shared.*;
import edu.duke.ece651.shared.dao.*;
import edu.duke.ece651.shared.model.*;

public class RemoveSectionController {

  private Stage mainStage;

  @FXML
  private TextField sectionIdField;

  public void setMainStage(Stage mainStage) {
    this.mainStage = mainStage;
  }

  @FXML
  private void removeSectionButton() {
    String sectionIdString = sectionIdField.getText().trim();
    if (sectionIdString.isEmpty()) {
      showAlert("Error", "Section ID cannot be empty", AlertType.ERROR);
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
    boolean isSuccess = removeSection(sectionId);
    if (isSuccess) {
      showAlert("Success", "Section removed successfully", AlertType.INFORMATION);
      clearForm();
    }
    else {
      showAlert("Error", "Failed to remove section", AlertType.ERROR);
    }
  }

  private boolean removeSection(int sectionId) {
    SectionDAO sectionIO = new SectionDAO();
    sectionIO.deleteSection(sectionId);
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
