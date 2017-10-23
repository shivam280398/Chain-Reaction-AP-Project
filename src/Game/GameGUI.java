package Game;

import java.util.Scanner;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GameGUI extends Application {
	public Grid grid;

	public static void main(String[] args) {
		launch(args);

	}

	@Override
	public void start(Stage mainStage) throws Exception {
		
		Scanner s = new Scanner(System.in);
		int m, n = 0;
		System.out.println("Enter Dimension Option:");
		System.out.println("1. 9X6");
		System.out.println("2. 15X10");
		int option = s.nextInt();
		if (option == 1) {
			m = 6;
			n = 9;
		} else {
			m = 10;
			n = 15;
		}
		mainStage.setTitle("Chain Reaction");
		HBox hbox = new HBox(5);
		Button undoBtn = new Button("UNDO");
		ObservableList<String> dropdown = FXCollections.observableArrayList("Start Again", "Exit");
		final ComboBox dropdownMenu = new ComboBox(dropdown);
		undoBtn.setId("Undo");
		hbox.getChildren().addAll(undoBtn, dropdownMenu);
		if (option == 1)
			hbox.setPadding(new Insets(10, 0, 5, 150));
		else
			hbox.setPadding(new Insets(10, 0, 5, 220));
		BorderPane root = new BorderPane();
		Scene mainScene = new Scene(root);
		mainStage.setScene(mainScene);
		mainScene.getStylesheets().add(getClass().getResource("/assets/stylesheetGame.css").toExternalForm());
		grid = new Grid(m, n);
		root.setTop(hbox);
		root.setCenter(grid);
		mainStage.show();
	}

}
