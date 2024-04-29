package edu.duke.ece651.courseManage.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.util.WaitForAsyncUtils;

import javafx.application.Platform;
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
import javafx.scene.input.MouseEvent;
import javafx.event.ActionEvent;

import org.testfx.api.FxRobot;

@ExtendWith(ApplicationExtension.class)
public class AddCourseControllerTest {
  private TextField Id;
  private TextField Name;
  private Button submitButton;
  private Button backButton;
  private AddCourseController cont;
  @Start
  private void start(Stage stage) {
    Id = new TextField();
    Name = new TextField();
    cont = new AddCourseController();
    cont.setFields(Id, Name);
  }
  @Test
  public void test_(FxRobot robot) {
    Platform.runLater(()->{
        Id.setText("123");
        Name.setText("New Course");
        //robot.clickOn("Back To Home Page.");
      });
    WaitForAsyncUtils.waitForFxEvents();
  }

}
