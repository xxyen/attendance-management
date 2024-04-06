/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package edu.duke.ece651.client;

import java.io.IOException;

public class App {
    public String getGreeting() {
        return "Hello World!";
    }

    public static void main(String[] args) throws IOException {
        try {
            ClientSocketHandler client = new ClientSocketHandler();
            String host = "localhost";
            int port = 1234;
            String testxml = "<request><message>Hello from Can Pei</message></request>";

            client.connectToServer(host, port);
            client.sendXmlToServer(testxml);
            client.readResponseFromServer();
            client.closeConnection();
        } catch (Exception e){
            System.out.println(e.getMessage());
        }

    }
}
