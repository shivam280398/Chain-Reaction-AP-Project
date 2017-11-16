package Settings;

import java.io.Serializable;

import java.util.ArrayList;

import javafx.scene.paint.Color;

/**
 * <h1>Settings class</h1> The program is used to implement Settings
 * functionality in the game.
 *
 *
 */
public class Settings implements Serializable {

	public int noOfPlayers;
	public Player[] players;

	/**
	 * This constructor is used to initialize the number of players in the game.
	 * 
	 * @param _noOfplayers
	 *            number of players
	 * @return none
	 */
	Settings(int _noOfplayers) {
		this.noOfPlayers = _noOfplayers;
		players = new Player[noOfPlayers];
		Color[] clr = new Color[8];
		clr[0] = Color.RED;
		clr[1] = Color.GREEN;
		clr[2] = Color.BLUE;
		clr[3] = Color.YELLOW;
		clr[4] = Color.VIOLET;
		clr[5] = Color.CRIMSON;
		clr[6] = Color.PINK;
		clr[7] = Color.YELLOWGREEN;
		for (int i = 0; i < noOfPlayers; i++) {
			players[i] = new Player(clr[i]);
		}
	}

	/**
	 * This method ensures that colorID is unique for each player in the game.
	 * 
	 * @param colorID
	 *            ColorID currently selected by the player.
	 * @param index
	 *            index of the player in the player array
	 * @throws ColorNotValidException
	 */
	public void isColorValid(Color colorID, int index) throws ColorNotValidException {
		for (int i = 0; i < players.length; i++) {
			if (i != index && players[i].colorID != null) {
				if (players[i].colorID.equals(String.valueOf(colorID)) == true) {
					throw new ColorNotValidException("Color already taken");
				}
			}
		}

	}

}

class ColorNotValidException extends Exception {
	public ColorNotValidException(String message) {
		super(message);
	}
}
