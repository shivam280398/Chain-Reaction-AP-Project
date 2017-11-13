package Settings;

import java.io.Serializable;

import javafx.scene.paint.Color;

public class Player implements Serializable{

	String colorID;
	int cells;

	public int getCells() {
		return cells;
	}

	public void setCells(int cells) {
		this.cells = cells;
	}

	Player() {
		colorID = null;
		cells = 0;
	}

	public Player(Color _colorID) {
		Color obcolor = _colorID;
		this.colorID = String.valueOf(obcolor);
	}

	public Color getColor() {
		Color coll = Color.valueOf(colorID);
		return coll;
	}

	public void setColor(Color _colorID) {
		this.colorID = String.valueOf(_colorID);
	}

}