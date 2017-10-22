package MainMenu;

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

	@Override
	public void start(Stage mainStage) throws Exception {
		// TODO Auto-generated method stub
		initMain(mainStage);
	}

	public static void main(String[] args) {
		launch(args);
	}

	public void initMain(Stage stage) {
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
		hbox.setPadding(new Insets(30, 20, 50, 80));
		Button playGameBtn = new Button("PLAY");
		Button settingsBtn = new Button("SETTINGS");
		Button quitBtn = new Button("QUIT");
		Button resumeBtn = new Button("RESUME");
		Text noOfPlayers = new Text("No Of Players");
		noOfPlayers.setId("noOfP");
		//noOfPlayers.text
		noOfPlayers.setFill(Color.WHITE);
		TextField noOfPlayerstf = new TextField();
		noOfPlayerstf.setMaxWidth(30);
		Text gridSizet = new Text("          Grid Size");
		gridSizet.setFill(Color.WHITE);
		ObservableList<String> gridSize = FXCollections.observableArrayList("9X6","15X10");
		final ComboBox gridSizeBox = new ComboBox(gridSize);
		playGameBtn.setMinWidth(120);
		settingsBtn.setMinWidth(120);
		quitBtn.setMinWidth(120);
		resumeBtn.setMinWidth(120);
		hbox.getChildren().addAll(noOfPlayers,noOfPlayerstf,gridSizet,gridSizeBox);
		flow.getChildren().addAll(playGameBtn, settingsBtn, quitBtn, resumeBtn);
		//root.setCenter();
		subroot.getChildren().addAll(hbox,flow);
		//AnchorPane.setBottomAnchor(flow, 10d);
		//AnchorPane.setTopAnchor(hbox, 2d);
		
		root.setCenter(subroot);

		stage.setScene(mainScene);
		stage.show();

	}

}
