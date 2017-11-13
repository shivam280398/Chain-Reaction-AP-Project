package Game;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Scanner;
import Settings.Player;
import Settings.Settings;
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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class GameGUI extends Application {
	
	public Grid grid;
	private int noOfplayers;
	private String size;
	public Player[] players;
	public SettingsGUI setting;
	public int count;
	int m = 0,n = 0;
	
	public GameGUI(String c, String _n,SettingsGUI _setting) {
		
		size=c;
		this.setting = _setting;
		noOfplayers=Integer.parseInt(_n);
		if(_setting!=null){
		players= setting.setting.players;
		}
		else{
			players = new Player[noOfplayers];
			Color[] clr=new Color[8];
			clr[0]=Color.RED;
			clr[1]=Color.YELLOW;
			clr[2]=Color.BLUE;
			clr[3]=Color.BROWN;
			clr[4]=Color.VIOLET;
			clr[5]=Color.CRIMSON;
			clr[6]=Color.HOTPINK;
			clr[7]=Color.YELLOWGREEN;
			for(int i=0;i<noOfplayers;i++) {
				players[i]=new Player(clr[i]);
			}
			
		//	setting.setting.players = players;
		}
		
		
		count = 0;
		if(size.equals("9X6")) {
			m=6;
			n=9;
		}
		else {
			m=10;
			n=15;
		}
		grid = new Grid(m, n,players,count);
		
	}
	
	public GameGUI(String _size,int _n,Player[] plist,int turn,GridStatus _grid){
		
		size = _size;
		noOfplayers = _n;
		players = plist;
		count = turn;
		grid = new Grid(_grid,players);
		if(size.equals("9X6")) {
			m=6;
			n=9;
		}
		else {
			m=10;
			n=15;
		}
	}
	
	public int getNoOfplayers() {
		return noOfplayers;
	}
	
	public void setNoOfplayers(int noOfplayers) {
		this.noOfplayers = noOfplayers;
	}
	
	public String getSize() {
		return size;
	}
	
	public void setSize(String size) {
		this.size = size;
	}
	
	public static void main(String[] args) {
		launch(args);

	}
	
	@Override
	public void start(Stage mainStage) throws Exception {
		
		Scanner s = new Scanner(System.in);
		mainStage.setTitle("Chain Reaction");
		HBox hbox = new HBox(5);
		Button undoBtn = new Button("UNDO");
		ObservableList<String> dropdown = FXCollections.observableArrayList("Start Again", "Exit");
		final ComboBox dropdownMenu = new ComboBox(dropdown);
		undoBtn.setId("Undo");
		hbox.getChildren().addAll(undoBtn, dropdownMenu);
		if (m==6)
			hbox.setPadding(new Insets(10, 0, 5, 150));
		else
			hbox.setPadding(new Insets(10, 0, 5, 220));
		BorderPane root = new BorderPane();
		Scene mainScene = new Scene(root);
		mainStage.setScene(mainScene);
		mainScene.getStylesheets().add(getClass().getResource("/assets/stylesheetGame.css").toExternalForm());
		root.setTop(hbox);
		root.setCenter(grid);
		GameGUIStatus gs = new GameGUIStatus(noOfplayers, players, size,grid.gridst);
		//System.out.println(gs.count);
		gs.serialize("GamePlay", gs);
		mainStage.setOnCloseRequest( event -> {try {
			GameGUIStatus.serialize("GamePlay",gs);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}} );
		
		undoBtn.setOnMouseClicked(event -> {
			try {
				System.out.println("LIT AF");
				grid.saveState();
				//grid.gridundo.print();
				//GridStatus.serialize("GameUndo",grid.gridundo);
				GridStatus gsundo = GridStatus.deserialize("GameUndo");
				System.out.println("Sahi aaja bhai");
				gsundo.print();
				grid = new Grid(gsundo,players);
				  //  gsundo.grid.print();
					//System.out.println(gsundo.count);
				//stage.close();
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		boolean check=true;
		mainStage.show(); 
	}
	public static boolean checkPlayers(Player[] players) {
		int count=0;
		boolean flag=true;
		for(int i=0;i<players.length;i++) {
			if(players[i]==null) {
				count++;
			}
			else if(players[i].getCells()==0) {
				players[i]=null;
				count++;
			}
		}
		if(count==players.length-1) {
			flag=false;
			for(int i=0;i<players.length;i++) {
				if(players[i]!=null) {
					System.out.println("Player "+i+" wins");
					break;
				}
			}
		}
		return flag;
	}
	
}