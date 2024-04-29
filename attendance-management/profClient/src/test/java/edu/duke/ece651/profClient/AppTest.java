package edu.duke.ece651.profClient;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import javafx.stage.Stage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.testfx.framework.junit5.ApplicationTest;
import java.io.IOException;

class AppTest extends ApplicationTest {
    private App app;

    @BeforeEach
    public void setUp() throws Exception {
        app = new App();
    }

    @AfterEach
    public void tearDown() throws Exception {
        app.stop();
    }

    // @Test
    // public void testStart(Stage stage) throws Exception {
    //     app.start(stage);
    // }

}