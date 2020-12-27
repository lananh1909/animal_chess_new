package game;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Game extends Application {
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		try {
			System.out.println("Hoan");
			System.out.println("Duong");
			Parent root = FXMLLoader.load(this.getClass().getResource("Template.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
