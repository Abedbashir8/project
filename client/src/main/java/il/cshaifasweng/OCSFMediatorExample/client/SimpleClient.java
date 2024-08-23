package il.cshaifasweng.OCSFMediatorExample.client;
import il.cshaifasweng.OCSFMediatorExample.client.moviesListController;
import il.cshaifasweng.OCSFMediatorExample.client.ocsf.AbstractClient;
import il.cshaifasweng.OCSFMediatorExample.entities.Message;
import il.cshaifasweng.OCSFMediatorExample.entities.Warning;
import javafx.application.Platform;
import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.List;

public class SimpleClient extends AbstractClient {

    private static SimpleClient client = null;
    public static String newHost = "";
    public static int newPort = 3002;


    private SimpleClient(String localhost, int port) {
        super(localhost, port);
    }

    protected void handleMessageFromServer(Object msg) {

        if (msg.getClass().equals(Warning.class)) {
            EventBus.getDefault().post(new WarningEvent((Warning)msg));
        }
        System.out.println("f");
        if (msg instanceof List) {

            moviesListController.allTheMovies = (List)msg;
            Platform.runLater(() -> {
                try {
                    App.setRoot("moviesList");
                    App.firstStage.setTitle("Movies list");
                } catch (IOException var1) {
                    throw new RuntimeException(var1);
                }
            });
        }

    }

    public static SimpleClient getClient() {
        if (client == null) {
            client = new SimpleClient(newHost, newPort);
        }
        return client;
    }
}
