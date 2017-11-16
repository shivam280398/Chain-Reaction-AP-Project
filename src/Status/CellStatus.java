package Status;

import java.io.IOException;
import java.io.Serializable;

import Settings.Player;

/**
 * <h1>Storing the State of the Cell</h1> The program is used to store the state
 * of each cell in grid.
 *
 *
 */

public class CellStatus implements Serializable {

	public int noOfOrbs;
	public Player currentOwner;

	/**
	 * This constructor is used to initialize the cell state.
	 * 
	 * @param n
	 *            This is the initial number of orbs in the cell.
	 * @param player
	 *            This is the current owner of the cell.
	 */
	public CellStatus(int n, Player player) {
		noOfOrbs = n;
		currentOwner = player;
	}

	public int getNoOfOrbs() {
		return noOfOrbs;
	}

	public void setNoOfOrbs(int noOfOrbs) {
		this.noOfOrbs = noOfOrbs;
	}

	public Player getCurrentOwner() {
		return currentOwner;
	}

	public void setCurrentOwner(Player currentOwner) {
		this.currentOwner = currentOwner;
	}
	
	

}
