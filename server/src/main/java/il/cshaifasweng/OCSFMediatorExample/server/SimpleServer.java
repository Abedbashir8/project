//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package il.cshaifasweng.OCSFMediatorExample.server;

import il.cshaifasweng.OCSFMediatorExample.entities.Movie;
import il.cshaifasweng.OCSFMediatorExample.entities.Warning;
import il.cshaifasweng.OCSFMediatorExample.server.ocsf.AbstractServer;
import il.cshaifasweng.OCSFMediatorExample.server.ocsf.ConnectionToClient;
import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class SimpleServer extends AbstractServer {


    public SimpleServer(int port) {
        super(port);
    }

    protected void handleMessageFromClient(Object msg, ConnectionToClient client) throws Exception {
        String msgString = msg.toString();
        if (msgString.startsWith("#warning")) {
            Warning warning = new Warning("Warning from server!");

            try {
                client.sendToClient(warning);
                System.out.format("Sent warning to client %s\n", client.getInetAddress().getHostAddress());
            } catch (IOException var8) {
                var8.printStackTrace();
            }
        }

        if (msgString.startsWith("Get all Movies")) {
            List<Movie> temp = ConnectToDataBase.getAllMovies();
            client.sendToClient(temp);
        }
        String selected;
        String[] parts;
        if (msgString.startsWith("Update time")) {
            parts = msgString.split("@");
            System.out.println("HI2");
            ConnectToDataBase.updateShowtime(parts[2], parts[1]); // parts[2] is the name, and parts[1] is thw new time.
            List<Movie> temp = ConnectToDataBase.getAllMovies();
            client.sendToClient(temp);
        }




    }



    private void sendWarningToClient(ConnectionToClient client, String message) {
        Warning warning = new Warning(message);

        try {
            client.sendToClient(warning);
            System.out.format("Sent warning to client %s: %s\n", client.getInetAddress().getHostAddress(), message);
        } catch (IOException var5) {
            var5.printStackTrace();
        }

    }
}
