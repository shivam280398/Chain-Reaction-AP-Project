package Settings;

import java.io.Serializable;

import javafx.scene.paint.Color;

/**
 * <h1>Player class</h1> The program is used to store the state of each player
 * in the game.
 *
 *
 */
public class Player implements Serializable {

	String colorID;
	int cells;

	/**
	 * This is an empty constructor and is used to initialize the player state.
	 * 
	 * @param none
	 * @return none
	 */
	Player() {
		colorID = null;
		cells = 0;
	}

	/**
	 * This constructor is used to initialize the player's colorID.
	 * 
	 * @param _colorID
	 *            This is the colorID of the player's orbs in the game.
	 */
	public Player(Color _colorID) {
		Color obcolor = _colorID;
		this.colorID = String.valueOf(obcolor);
	}

	/**
	 * This method is used to get the number of cells currently owned by the
	 * player in the grid.
	 * 
	 * @return cells the numbers of cells owned currently.
	 */
	public int getCells() {
		return cells;
	}

	/**
	 * This method is used to set the number of cells currently owned by the
	 * player in the grid.
	 * 
	 * @param cells
	 *            number of cells
	 */
	public void setCells(int cells) {
		this.cells = cells;
	}

	/**
	 * This method is used to get the colorID of orbs of the player in the grid.
	 * 
	 * @return Color ColorID of orbs owned by player.
	 */
	public Color getColor() {
		Color coll = Color.valueOf(colorID);
		return coll;
	}

	/**
	 * This method is used to set the colorID of orbs of the player in the grid.
	 * 
	 * @param _colorID
	 *            ColorID of orbs owned by player.
	 */
	public void setColor(Color _colorID) {
		this.colorID = String.valueOf(_colorID);
	}

}