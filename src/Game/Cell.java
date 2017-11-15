package Game;

import java.io.Serializable;

import Settings.Player;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;

public class Cell extends Button{
	private int x;
	private int y;
	private int criticalMass;
	private int orbs;
	private Player p;

	public Player getP() {
		return p;
	}

	public void setP(Player p) {
		this.p = p;
	}

	public Cell(int x1, int y1) {
		super();
		this.x = x1;
		this.y = y1;
	}

	public int getOrbs() {
		return orbs;
	}

	public void setOrbs(int orbs) {
		this.orbs = orbs;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getCriticalMass() {
		return criticalMass;
	}

	public void setCriticalMass(int criticalMass) {
		this.criticalMass = criticalMass;
	}
	
	public void setColor(Color color){
		
		
	}
}