package edu.duke.ece651.server;

import static org.junit.jupiter.api.Assertions.*;

import edu.duke.ece651.shared.*;
import edu.duke.ece651.shared.model.Section;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;

class ClientHandlerTest {
    @Test
    void test_loop() throws Exception {
        String inputData = "dfasf\n" +
                "8\n" +
                "1\n" +
                "asfs\n" +
                "asfd\n" +
                "1\n" +
                "prof001\n" +
                "asfd\n" +
                "1\n" +
                "prof001\n" +
                "123\n" +
                "asdf\n" +
                "888\n" +
                "1\n" +
                "fwqfsd\n" +
                "1\n" +
                "888\n" +
                "1\n" +
                "1\n" +
                "4\n" +
                "2\n" +
                "1\n" +
                "stu001\n" +
                "123\n" +
                "wqfesd\n" +
                "w\n" +
                "8\n" +
                "1\n" +
                "asfdas\n" +
                "1\n" +
                "8888\n" +
                "1\n" +
                "1\n" +
                "3\n" +
                "2\n" +
                "2\n";
        BufferedReader input = new BufferedReader(new StringReader(inputData));
        PrintStream output = new PrintStream(new ByteArrayOutputStream(), true);
        ClientHandler c = new ClientHandler(new Socket());
        c.mainLoop(input, output);

    }


}