package il.cshaifasweng.OCSFMediatorExample.client;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import javax.swing.*;
import java.io.IOException;
import java.time.format.DateTimeFormatter;

/**
 * JavaFX App
 */
public class App extends Application {

    public static Stage firstStage;
    private static Scene scene;
    public static SimpleClient client;

    @Override
    public void start(Stage stage) throws IOException {
        firstStage = stage;
        EventBus.getDefault().register(this);
        scene = new Scene(loadFXML("initConnection"), 640, 480);
        firstStage.setScene(scene);
        firstStage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
        firstStage.setTitle(fxml);
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    @Override
    public void stop() throws Exception {
        EventBus.getDefault().unregister(this);
        client.closeConnection();
        super.stop();
    }

    @Subscribe
    public void onMessageEvent(MessageEvent message) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION,
                    String.format("Message:\nId: %d\nData: %s\n",
                            message.getMessage().getId(),
                            message.getMessage().getMessage()));
            alert.setTitle("New Message");
            alert.setHeaderText("New Message:");
            alert.show();
        });
    }
    public static Scene getScene() {
        return scene;
    }

    public static Stage getStage() {
        return firstStage;
    }
    public static void main(String[] args)  throws Exception {
        launch(args);
    }
}
