package Status;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import Game.Grid;
import Settings.Player;
import Settings.SettingsGUI;

public class GameGUIStatus implements Serializable{

	public GridStatus grid;
	public int noOfplayers;
	public String size;
	public Player[] players;
	
	
	public GameGUIStatus(int _n,Player[] _p,String _size,GridStatus _grid){
		
		noOfplayers = _n;
		players = _p;
		size = _size;
		grid = _grid;
		
	}
	
	public GameGUIStatus() {
		// TODO Auto-generated constructor stub
	}

	public static void serialize(String pathname, GameGUIStatus pl) throws FileNotFoundException, IOException {
		ObjectOutputStream out = null;
		try {

			out = new ObjectOutputStream(new FileOutputStream("src/" + pathname + ".txt"));
			out.writeObject(pl);

		} finally {
			out.close();
		}
	}

	public static GameGUIStatus deserialize(String pathname) throws IOException, ClassNotFoundException {
		ObjectInputStream in = null;
		GameGUIStatus gameGUI = null;
		try {
			in = new ObjectInputStream(new FileInputStream("src/" + pathname + ".txt"));
			gameGUI = (GameGUIStatus) in.readObject();

		} finally {
			in.close();
		}
		return gameGUI;
	}

}	
	

