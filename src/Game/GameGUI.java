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
		System.out.println("Enter Dimensions");
		int m = s.nextInt();
		int n = s.nextInt();
		mainStage.setTitle("Chain Reaction");
		HBox hbox = new HBox(5);
		Button undoBtn = new Button("Undo");
		ObservableList<String> dropdown = FXCollections.observableArrayList("Start Again","Exit");
		final ComboBox dropdownMenu = new ComboBox(dropdown);
		
		hbox.getChildren().addAll(undoBtn, dropdownMenu);
		hbox.setPadding(new Insets(10, 10, 5, 50));
		BorderPane root = new BorderPane();
		Scene mainScene = new Scene(root);
	//	root.setPrefSize(500, 500);
		mainStage.setScene(mainScene);
		mainScene.getStylesheets().add(getClass().getResource("/assets/stylesheetGame.css").toExternalForm());
		grid = new Grid(m, n);
		root.setTop(hbox);
		root.setCenter(grid);
		mainStage.show();
	}
	public VBox add() {
		VBox vb = new VBox();
		vb.setPadding(new Insets(50, 70, 50, 70));
		vb.setSpacing(50);
		vb.setStyle("-fx-background-color: #28cc28;");
		Button b1 = new Button("Undo");
		b1.setMinSize(150, 40);
		Button b2 = new Button("Exit");
		b2.setMinSize(150, 40);
		vb.getChildren().addAll(b1, b2);
		return vb;
		
	}

}
