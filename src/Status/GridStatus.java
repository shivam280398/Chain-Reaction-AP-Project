package Status;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import Settings.Player;

/**
 * <h1>Storing the State of the Grid</h1> The GridStatus program simply stores
 * the state of the grid. The program is used to serialize and deserialize the
 * state of the game.
 *
 *
 */
public class GridStatus implements Serializable {

	public int height;
	public int width;
	public CellStatus[][] gridSt;
	public int turn;
	public int count = 0;
	public boolean winner = false;

	/**
	 * This constructor is used to initialize the grid state object.
	 * 
	 * @param height
	 *            This is the height of the grid.
	 * @param width
	 *            The is the width of the grid.
	 * @param counter
	 *            The variable stores the turn number in the game.
	 * 
	 */
	public GridStatus(int height, int width, int counter) {

		this.height = height;
		this.width = width;
		count = counter;
		gridSt = new CellStatus[height][width];
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				gridSt[i][j] = new CellStatus(0, null);
			}
		}

	}

	/**
	 * This method is used to set the current owner of a cell in the grid.
	 * 
	 * @param x x-coordinate of the cell.
	 * @param y y-coordinate of the cell.
	 * @param p current owner of the cell.
	 */
	public void setowner(int x, int y, Player p) {
		gridSt[x][y].setCurrentOwner(p);
	}

	
	/**
	 * This method is used to set the number of orbs in a  cell in the grid.
	 * 
	 * @param x x-coordinate of the cell.
	 * @param y y-coordinate of the cell.
	 * @param _n current number of orbs in the cell.
	 */
	public void setnumber(int x, int y, int _n) {
		gridSt[x][y].setNoOfOrbs(_n);
	}

	/**
	 * This method is used to update the turn number of the player in the game.
	 * 
	 * @param counter current turn number in the game.
	 */
	public void updateturn(int counter) {
		turn = counter;
	}
	
	
	/**
	 * This method is used to print the current status of the GridStatus class.
	 */
	public void print() {
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				System.out.print(gridSt[j][i].noOfOrbs + " ");
			}
			System.out.println();
		}
		System.out.println();
	}

}
