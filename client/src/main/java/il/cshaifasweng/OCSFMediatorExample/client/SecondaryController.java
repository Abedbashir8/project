package il.cshaifasweng.OCSFMediatorExample.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

public class SecondaryController {

    @FXML
    private Button secondaryButton;

    @FXML
    void switchToShowMovieScreen(ActionEvent event) {
        try {
            SimpleClient.getClient().sendToServer("Get all Movies");
        } catch (IOException var3) {
            throw new RuntimeException(var3);
        }
    }

}
