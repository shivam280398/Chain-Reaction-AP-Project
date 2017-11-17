package Game;

import java.io.Serializable;

import Settings.Player;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;

/**
 * <h1>Cell class</h1> The program is used to store
 *  each cell of the grid.
 *  This class extends Button class of javaFX.
 *
 */
public class Cell extends Button {
	private int x;
	private int y;
	private int criticalMass;
	private int orbs;
	private Player p;
	/**
	 * This constructor is used to initialize the player's colorID.
	 * 
	 * @param x1
	 * 		This is the coordinate of the cell along x axis.
	 * @param y1
	 * 		This is the coordinate of the cell along y axis.
	 **/
	public Cell(int x1, int y1) {
		super();
		this.x = x1;
		this.y = y1;
	}
	/**
	 * This method is used to get the player assigned to the cell.
	 * 
	 * @return player,current player assigned to the cell.
	 *  
	 */
	public Player getP() {
		return p;
	}
	/**
	 * This method is used to assign a player to the cell.
	 * 
	 * @param p
	 *            player
	 */
	public void setP(Player p) {
		this.p = p;
	}
	/**
	 * This method is used to get the number of orbs placed in the cell.
	 * 
	 * @return orbs
	 * 			the number of orbs in the cell.
	 */
	public int getOrbs() {
		return orbs;
	}
	/**
	 * This method is used to set the number of orbs in the cell.
	 * 
	 * @param orbs
	 *   		number of orbs
	 */
	public void setOrbs(int orbs) {
		this.orbs = orbs;
	}
	/**
	 * This method is used to get the coordinate of the cell
	 * along the x axis.
	 * @return x
	 * 			coordinate along the x axis.
	 */

	public int getX() {
		return x;
	}
	/**
	 * This method is used to set the coordinate of the cell
	 * along the x axis.
	 * 
	 * @param x
	 *  	coordinate along the x axis
	 */

	public void setX(int x) {
		this.x = x;
	}
	/**
	 * This method is used to get the coordinate of the cell
	 * along the y axis.
	 * @return y
	 * 			coordinate along the y axis.
	 */
	public int getY() {
		return y;
	}
	/**
	 * This method is used to set the coordinate of the cell
	 * along the y axis.
	 * 
	 * @param y
	 *  	coordinate along the y axis
	 */

	public void setY(int y) {
		this.y = y;
	}
	/**
	 * This method is used to get the critical mass of the cell.
	 * @return criticalMass
	 * 			critical mass of the cell.
	 */

	public int getCriticalMass() {
		return criticalMass;
	}
	/**
	 * This method is used to criticalMass of the cell.
	 * 
	 * @param criticalMass
	 *  	criticalMass of the cell
	 */
	public void setCriticalMass(int criticalMass) {
		this.criticalMass = criticalMass;
	}
}
