import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.api.FxRobot;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Label;

import static org.testfx.assertions.api.Assertions.assertThat;

@ExtendWith(ApplicationExtension.class)
public class LoginControllerTest {

    @Start
    private void start(Stage stage) throws Exception {
      System.setProperty("testfx.robot", "glass");
      System.setProperty("glass.platform", "Monocle");
      System.setProperty("monocle.platform", "Headless");

      FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Login.fxml"));
      Parent mainNode = loader.load();
      stage.setScene(new Scene(mainNode));
      stage.show();
      stage.toFront();
    }

    @Test
    void testInitialStageContainsUsernameAndPasswordFields(FxRobot robot) {
        TextField usernameField = robot.lookup("#usernameField").queryAs(TextField.class);
        assertThat(usernameField).isNotNull();
        assertThat(usernameField.getText()).isEmpty();

        PasswordField passwordField = robot.lookup("#passwordField").queryAs(PasswordField.class);
        assertThat(passwordField).isNotNull();
        assertThat(passwordField.getText()).isEmpty();

        Label errorLabel = robot.lookup("#errorLabel").queryAs(Label.class);
        assertThat(errorLabel).isNotNull();
        assertThat(errorLabel.getText()).isEmpty();
    }
}
