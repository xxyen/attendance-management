package edu.duke.ece651.courseManage;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.StringReader;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.ResourceAccessMode;
import org.junit.jupiter.api.parallel.ResourceLock;
import org.junit.jupiter.api.parallel.Resources;
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
public class AppTest {
  App a;

  @Start
  public void start(Stage stage) throws IOException {
    a  = new App();
    a.start(stage);
  }

  @Test
  void test_(FxRobot robot) {
    //robot.clickOn("Course Management System");
    //robot.clickOn("1.  View the List of All Courses");
    //robot.clickOn("Back To Home Page.");
    //robot.clickOn("Exit Course Management System");
    //robot.clickOn("Exit System");
  }


}
