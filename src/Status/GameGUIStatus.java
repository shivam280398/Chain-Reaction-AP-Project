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

/**
 * <h1>Storing the State of the Game</h1> The GameGUIStatus program
 * simply stores the state of the game.
 * The program is used to serialize and deserialize the state of the game.
 *
 *
 */

public class GameGUIStatus implements Serializable {

	public GridStatus grid;
	public int noOfplayers;
	public String size;
	public Player[] players;

	/**
	 * This constructor is used to initialize the game state object.
	 * 
	 * @param n
	 *            This is the number of players in the game.
	 * @param p
	 *            This is the player array.
	 * @param size
	 *            This is the size of the grid.
	 * @param grid
	 *            This parameter stores the state of Grid.
	 */
	public GameGUIStatus(int n, Player[] p, String size, GridStatus grid) {

		this.noOfplayers = n;
		this.players = p;
		this.size = size;
		this.grid = grid;

	}

	/**
	 * This is an empty constructor.
	 */

	public GameGUIStatus() {
	}

	/**
	 * This method is used to serialize the game state object.
	 * 
	 * @param pathname
	 *            This is the pathname of the file in which the information
	 *            will be serialized.
	 * @param gameStatus
	 *            The object which is serialized.

	 * @exception IOException
	 * @exception FileNotFoundException
	 */
	public static void serialize(String pathname, GameGUIStatus gameStatus) throws FileNotFoundException, IOException {
		ObjectOutputStream out = null;
		try {

			out = new ObjectOutputStream(new FileOutputStream("src/" + pathname + ".txt"));
			out.writeObject(gameStatus);

		} finally {
			out.close();
		}
	}

	/**
	 * This method is used to deserialize the game state object.
	 * 
	 * @param pathname
	 *            This is the pathname of the file in which the information
	 *            will be deserialized.
	 * @return GameGUIStatus
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
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
