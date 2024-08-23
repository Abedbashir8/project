package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.Message;
import il.cshaifasweng.OCSFMediatorExample.entities.Movie;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;
import java.util.List;

public class PrimaryController {
	@FXML
	private Text text;

	@FXML
	private Button getAllMoviesBtn;

	@FXML
	private Button updateBtn;

	@FXML
	private ListView<Movie> viewAllMovies;

	@FXML
	private TextField viewGrades;

	@FXML
	private TextField viewGrades1;

	@FXML
	void getAllMovies(ActionEvent event) throws IOException {
		SimpleClient client = SimpleClient.getClient();
		Message message = new Message(0, "Get all Movies");
		client.sendToServer(message);
	}

//	@FXML
//	void getGrades(ActionEvent event) {
//
//	}

	@FXML
	void updateBtn(ActionEvent event) {

	}

	@Subscribe
	public void onMessageEvent(MessageEvent event) {
		List<Movie> movies = (List<Movie>) event.getMessage().getMessage();
		viewAllMovies.getItems().setAll(movies);
	}

	@FXML
	void initialize() {
		EventBus.getDefault().register(this);
		try {
			Message message = new Message(0, "add client");
			SimpleClient.getClient().sendToServer(message);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
