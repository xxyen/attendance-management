package duke.edu.ece651.studentclient;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class ClientTaskTest {
    @Disabled
    @Test
    public void testClientTask() throws Exception {
        // Setup your ClientTask with mocked objects
        ClientTask clientTask = new ClientTask("vcm-37924.vm.duke.edu", 12345, System.out::println);
        // You need to modify ClientTask to allow injecting mocks, or use reflection.

        // Execute your test
        clientTask.execute();
    }

}