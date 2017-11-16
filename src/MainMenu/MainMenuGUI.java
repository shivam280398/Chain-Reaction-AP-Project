package MainMenu;

import java.io.IOException;

import Game.GameGUI;
import Game.Grid;
import Settings.Player;
import Settings.SettingsGUI;
import Status.GameGUIStatus;
import Status.GridStatus;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MainMenuGUI extends Application {

	public SettingsGUI sett;
	public MainMenuGUI mainMenu;
	public GameGUI gameGUI;

	// public MainMenuGUI(){
	// //sett = new SettingsGUI();
	// //gameGUI = new GameGUI();
	// }

	@Override
	public void start(Stage mainStage) throws Exception {
		mainMenu = new MainMenuGUI();
		mainMenu.initMain(mainStage);
	}

	public static void main(String[] args) {
		launch(args);
	}

	public void initMain(Stage stage) throws ClassNotFoundException, IOException {

		BorderPane root = new BorderPane();
		Scene mainScene = new Scene(root);
		stage.setTitle("MainPage");

		root.setPrefSize(450, 500);
		Label label1 = new Label("Chain Reaction");
		root.setTop(label1);
		root.setId("pane");

		AnchorPane subroot = new AnchorPane();
		root.setPrefSize(450, 400);

		HBox hbox = new HBox(5);
		VBox flow = new VBox(5);
		flow.setPrefWidth(100);
		mainScene.getStylesheets().add(getClass().getResource("/assets/stylesheetMain.css").toExternalForm());
		flow.setPadding(new Insets(70, 20, 60, 160));

		Button playGameBtn = new Button("PLAY");
		Button settingsBtn = new Button("SETTINGS");
		Button quitBtn = new Button("QUIT");
		Button resumeBtn = new Button("RESUME");

		Text noOfPlayers = new Text("No Of Players");
		noOfPlayers.setId("noOfP");
		noOfPlayers.setFill(Color.WHITE);
		TextField noOfPlayerstf = new TextField();
		noOfPlayerstf.setMaxWidth(30);
		Text gridSizet = new Text("          Grid Size");
		gridSizet.setFill(Color.WHITE);

		ObservableList<String> gridSize = FXCollections.observableArrayList("9X6", "15X10");
		final ComboBox gridSizeBox = new ComboBox(gridSize);

		settingsBtn.setOnMouseClicked(event -> {
			try {
				sett = new SettingsGUI(noOfPlayerstf.getText(), stage);
				sett.start(new Stage());
				// stage.close();
				stage.hide();
			} catch (Exception e) {
				e.printStackTrace();
			}
		});

		playGameBtn.setOnMouseClicked(event -> {
			try {
				if (!noOfPlayerstf.getText().equals("") && gridSizeBox.getValue() != null) {

					gameGUI = new GameGUI((String) gridSizeBox.getValue(), noOfPlayerstf.getText(), sett);
					gameGUI.start(new Stage());
					stage.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		});

		GameGUIStatus gsresume = GameGUIStatus.deserialize("GamePlay");

		resumeBtn.setOnMouseClicked(event -> {
			try {

				gameGUI = new GameGUI(gsresume.size, gsresume.noOfplayers, gsresume.players, gsresume.grid.count,
						gsresume.grid);
				gameGUI.start(new Stage());
				stage.close();

			} catch (Exception e) {
				e.printStackTrace();
			}
		});

		quitBtn.setOnMouseClicked(event -> {
			stage.close();
		});
		
		playGameBtn.setMinWidth(120);
		settingsBtn.setMinWidth(120);
		quitBtn.setMinWidth(120);
		resumeBtn.setMinWidth(120);

		hbox.getChildren().addAll(noOfPlayers, noOfPlayerstf, gridSizet, gridSizeBox);
		if (gsresume.grid.winner == false) {

			flow.getChildren().addAll(playGameBtn, settingsBtn, quitBtn, resumeBtn);
			hbox.setPadding(new Insets(30, 20, 50, 80));
		} else {

			hbox.setPadding(new Insets(20, 10, 50, 80));
			flow.getChildren().addAll(playGameBtn, settingsBtn, quitBtn);
		}
		subroot.getChildren().addAll(hbox, flow);
		root.setCenter(subroot);
		stage.setScene(mainScene);
		stage.show();
	}

}