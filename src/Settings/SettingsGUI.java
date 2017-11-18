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


/**
 * <h1>Settings GUI class</h1> The program is used to implement GUI for the Settings
 * functionality in the game.
 *
 *
 */
public class SettingsGUI extends Application {

	public int noOfplayers;
	public Settings setting;
	public Stage mainMenuStage;

	
	/**
	 * This constructor is used to initialize the number of players in the game.
	 * 
	 * @param _noOfPlayers
	 *            number of players
	 * @param _mainMenu The mainMenu Stage to invoke mainStage back from Settings GUI
	 */
	public SettingsGUI(String _noOfPlayers, Stage _mainMenu) {
		int nPlayers = Integer.parseInt(_noOfPlayers);
		this.noOfplayers = nPlayers;
		setting = new Settings(noOfplayers);
		mainMenuStage = _mainMenu;
	}

	
	/**
	 * This method is the main entry point for this JavaFX application.
	 * 
	 * @param mainStage mainStage of the GUI
	 */
	@Override
	public void start(Stage mainStage) throws Exception {
		// TODO Auto-generated method stub
		initSettingsUI(mainStage);
	}

	/**
	 * This method is used to launch the GUI for Settings class.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}

	/**
	 * This method is used as the application initialization method. This method is called immediately after 
	 * the Application class is loaded and constructed. 
	 * 
	 * @param mainStage mainStage of GUI
	 */
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

		mainmenuBtnAction(mainmenuBtn, mainStage);
		settingsListaction(settingsListView, mainStage);

		root.setCenter(box);
		root.setBottom(hbButton);

		mainStage.show();
	}
	
	
	/**
	 * This method performs the action when the MainMenu button is pressed. It defines the actionPerformed 
	 * method of the ActionListener of MainMenu Button.
	 * @param mainmenuBtn MainMenu Button 
	 * @param mainStage mainStage of GUI
	 */
	public void mainmenuBtnAction(Button mainmenuBtn, Stage mainStage) {
		mainmenuBtn.setOnMouseClicked(event -> {
			try {
				mainStage.close();
				mainMenuStage.show();

			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	/**
	 * This method performs the action whenever any button from the Player's list is selected. It defines the actionPerformed 
	 * method of the ActionListener of settingsListView.
	 * @param settingsListView Listview containing each Player's colorID.
	 * @param mainStage mainStage of GUI
	 */
	public void settingsListaction(ListView<String> settingsListView, Stage mainStage) {
		settingsListView.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				int index = settingsListView.getSelectionModel().getSelectedIndex();
				if (index < noOfplayers) {
					colorPicker(setting.players[index], mainStage,index);
				}
			}
		});
	}

	/**
	 * This method defines the GUI for colorPicker for each Player in the game.
	 * @param player The currently active player.
	 * @param stage mainStage of GUI.
	 * @param index index of currently active player.
	 */
	public void colorPicker(Player player, Stage stage,int index) {

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
		colorPicker.setValue(player.getColor());

		final Circle circle = new Circle(50);
		circle.setFill(colorPicker.getValue());

		colorpickerAction(circle, colorPicker);
		backBtnAction(backBtn, stage);
		okBtnAction(okBtn, colorPicker, player,index);

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

	/**
	 * This method performs the action when the OK button is pressed in ColorPicker Window. It defines the actionPerformed 
	 * method of the ActionListener of OK Button.
	 * @param okBtn okBtn Button 
	 * @param colorPicker mainStage of GUI.
	 * @param player Currently active player.
	 * @param index Index of the currently active player.
	 */
	public void okBtnAction(Button okBtn, ColorPicker colorPicker, Player player,int index) {
		okBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent t) {
				Boolean colorValid = true;
				Color colorID = colorPicker.getValue();
				String obcolor = String.valueOf(colorID);
				try{
					setting.isColorValid(colorID,index);
				}
				catch (ColorNotValidException e) {
					colorValid = false;
				}

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
	}

	/**
	 * This method performs the action when the Back button is pressed. It defines the actionPerformed 
	 * method of the ActionListener of Back Button.
	 * @param backBtn Back Button in the GUI
	 * @param stage mainStage of GUI
	 */
	public void backBtnAction(Button backBtn, Stage stage) {
		backBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent t) {

				initSettingsUI(stage);
			}
		});
	}
	
	
	/**
	 * This method performs the action when the color is selected from colorPicker. It defines the actionPerformed 
	 * method of the ActionListener of ColorPicker.
	 * @param circle A 2-D figure to demonstrate the color selected.
	 * @param colorPicker The colorPicker object
	 */
	public void colorpickerAction(Circle circle, ColorPicker colorPicker) {
		colorPicker.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent t) {
				Color colorID = colorPicker.getValue();
				circle.setFill(colorPicker.getValue());
			}
		});

	}
}
