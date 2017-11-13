package Settings;

import MainMenu.MainMenuGUI;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class SettingsGUI extends Application {

	public int noOfplayers;
	public Settings setting;
	public MainMenuGUI mainMenu;
	
	public SettingsGUI(String _noOfPlayers,MainMenuGUI _mainMenu){
		int nPlayers=Integer.parseInt(_noOfPlayers);
		this.noOfplayers = nPlayers;
		setting = new Settings(noOfplayers);
		mainMenu = _mainMenu;
		//System.out.println(mainMenu.sett.noOfplayers);
	}
	
	 

	@Override
	public void start(Stage mainStage) throws Exception {
		// TODO Auto-generated method stub
		initSettingsUI(mainStage);
	}

	public static void main(String[] args) {
		launch(args);
	}

	public void initSettingsUI(Stage mainStage) {
		BorderPane root = new BorderPane();
		Scene mainScene = new Scene(root);
		mainStage.setTitle("Settings");
		root.setId("settings");
		root.setPrefSize(400, 500);
		root.setPadding(new Insets(20));
		mainScene.getStylesheets().add(getClass().getResource("/assets/stylesheetSettings.css").toExternalForm());
		mainStage.setScene(mainScene);
		Label label1 = new Label("Settings");
		root.setTop(label1);
		label1.setId("SettingsLabel");
		
		ObservableList<String> labels = FXCollections.observableArrayList("Player1", "Player2", "Player3", "Player4",
				"Player5", "Player6", "Player7", "Player8");
		ListView<String> settingsListView = new ListView<String>(labels);
		
		
	    Button mainmenuBtn = new Button();
	    mainmenuBtn.setText("Main Menu");
	    HBox hbButton = new HBox(mainmenuBtn);
	    VBox box = new VBox(5, settingsListView);
		box.setPadding(new Insets(10, 20, 20, 10));
		box.setMaxHeight(280);
		
		mainmenuBtn.setOnMouseClicked(event -> {
			try {
				mainMenu.start( new Stage());
				mainStage.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		settingsListView.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				System.out.println("clicked on " + settingsListView.getSelectionModel().getSelectedItem());
				int index = settingsListView.getSelectionModel().getSelectedIndex();
				if(index<noOfplayers){
				colorPicker(setting.players[index], mainStage);
			    }
			}
		});
		root.setCenter(box);
		root.setBottom(hbButton);
		
		mainStage.show();
	}

	public void colorPicker(Player player, Stage stage) {

		AnchorPane anchorpane = new AnchorPane();
		Scene scene = new Scene(anchorpane, 400, 400);
		Button okBtn = new Button("OK");
		Button backBtn = new Button("BACK");
		HBox box = new HBox(5, okBtn, backBtn);
		anchorpane.setId("ColorPalette");
		box.setPadding(new Insets(10, 20, 20, 10));
		scene.getStylesheets().add(getClass().getResource("/assets/stylesheetSettings.css").toExternalForm());

		ColorPicker colorPicker = new ColorPicker();
		colorPicker.getStyleClass().add("split-button");
		colorPicker.setValue(Color.BLUE);

		final Circle circle = new Circle(50);
		circle.setFill(colorPicker.getValue());

		colorPicker.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent t) {
				Color colorID = colorPicker.getValue();
				circle.setFill(colorPicker.getValue());
			}
		});

		backBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent t) {

				initSettingsUI(stage);
			}
		});

		okBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent t) {

				Color colorID = colorPicker.getValue();
				System.out.println(colorID);
				String obcolor = String.valueOf(colorID);
				System.out.println(obcolor);
				System.out.println(Color.valueOf(obcolor));
				boolean colorValid = setting.isColorValid(colorID);
				
				if (colorValid == true) {
					player.setColor(colorID);
				} else {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error Dialog");
					alert.setHeaderText("Color Already selected!!");
					alert.setContentText("Please choose another color");
					alert.showAndWait();
				}
			}
		});

		FlowPane flowPane = new FlowPane();
		flowPane.setPadding(new Insets(10));
		flowPane.setHgap(10);
		flowPane.getChildren().addAll(circle, colorPicker);
		anchorpane.getChildren().addAll(box, flowPane);
		AnchorPane.setRightAnchor(box, 10d);
		AnchorPane.setBottomAnchor(box, 10d);
		AnchorPane.setTopAnchor(flowPane, 10d);
		AnchorPane.setLeftAnchor(flowPane, 10d);
		stage.setScene(scene);
		stage.show();

	}
}
