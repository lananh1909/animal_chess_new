package game;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Controller implements Initializable {

	@FXML
	private Button singlePlayer;
	@FXML
	private Button twoPlayers;
	@FXML
	private Button instructionButton;
	@FXML
	private Button quitGameButton;
	@FXML
	private AnchorPane levelSelectPane;
	@FXML
	private Button backFromLevelSelect;
	@FXML
	private Button easyButton;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		levelSelectPane.setVisible(false);
	}

	@FXML
	private void handleQuitGameButton(ActionEvent event) {
		Platform.exit();
	}

	@FXML
	public void changeScene(ActionEvent e) {
		Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("Instruction.fxml"));
		Parent parent = null;
		try {
			parent = loader.load();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		Scene scene = new Scene(parent);
		stage.setScene(scene);
	}

	@FXML
	public void singlePlayerSelect(ActionEvent e) {
		levelSelectPane.setVisible(true);
	}

	@FXML
	public void backFromLevelSelect(ActionEvent e) {
		levelSelectPane.setVisible(false);
	}

	@FXML
	public void startGame(ActionEvent e) throws IOException {
		Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("Boardgame.fxml"));
		Parent root = (Parent) loader.load();
		GameController gameController = loader.getController();
		stage.setScene(new Scene(root));
		stage.show();
	}
}
