package edu.duke.ece651.courseManage.controller;

import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.Node;

import java.io.IOException;

public class UpdateController {
  
  private Stage mainStage;
  
  public void setMainStage(Stage mainStage) {
    this.mainStage = mainStage;
  }

  @FXML
  private void changeCourseName() throws Exception {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/changeCourseNamePg.xml"));
    Parent changeCourseNamePage = loader.load();
    // Set the controller for the view course page
    ChangeCourseNameController controller = loader.getController();
    controller.setMainStage(mainStage);
    // Set the new scene on the existing stage
    Scene scene = new Scene(changeCourseNamePage, 960, 720);
    mainStage.setScene(scene);
    mainStage.show();
  }

  @FXML
  private void viewProfs() throws Exception {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/viewProfPg.xml"));
    Parent profPage = loader.load();
    // Set the controller for the view course page
    ViewProfController controller = loader.getController();
    controller.setMainStage(mainStage);
    // Set the new scene on the existing stage
    Scene scene = new Scene(profPage, 960, 720);
    mainStage.setScene(scene);
    mainStage.show();
  }

  @FXML
  private void viewSections() throws Exception {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/viewSectionPg.xml"));
    Parent sectionPage = loader.load();
    // Set the controller for the view course page
    ViewSectionController controller = loader.getController();
    controller.setMainStage(mainStage);
    // Set the new scene on the existing stage
    Scene scene = new Scene(sectionPage, 960, 720);
    mainStage.setScene(scene);
    mainStage.show();
  }

  @FXML
  private void addSection() throws Exception {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/addSectionPg.xml"));
    Parent addSectionPage = loader.load();
    // Set the controller for the view course page
    AddSectionController controller = loader.getController();
    controller.setMainStage(mainStage);
    // Set the new scene on the existing stage
    Scene scene = new Scene(addSectionPage, 960, 720);
    mainStage.setScene(scene);
    mainStage.show();
  }

  @FXML
  private void removeSection() throws Exception {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/removeSectionPg.xml"));
    Parent removeSectionPage = loader.load();
    // Set the controller for the view course page
    RemoveSectionController controller = loader.getController();
    controller.setMainStage(mainStage);
    // Set the new scene on the existing stage
    Scene scene = new Scene(removeSectionPage, 960, 720);
    mainStage.setScene(scene);
    mainStage.show();
  }

  @FXML
  private void viewStus() throws Exception {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/viewStuPg.xml"));
    Parent stuPage = loader.load();
    // Set the controller for the view course page
    ViewStuController controller = loader.getController();
    controller.setMainStage(mainStage);
    // Set the new scene on the existing stage
    Scene scene = new Scene(stuPage, 960, 720);
    mainStage.setScene(scene);
    mainStage.show();
  }

  @FXML
  private void viewStusInSection() throws Exception {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/viewStuInSectionPg.xml"));
    Parent stuInSectionPage = loader.load();
    // Set the controller for the view course page
    ViewStuInSectionController controller = loader.getController();
    controller.setMainStage(mainStage);
    // Set the new scene on the existing stage
    Scene scene = new Scene(stuInSectionPage, 960, 720);
    mainStage.setScene(scene);
    mainStage.show();
  }
  
  @FXML
  private void backToHome() throws Exception {
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
