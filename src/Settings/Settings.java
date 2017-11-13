package Settings;

import java.io.Serializable;
import java.util.ArrayList;

import javafx.scene.paint.Color;

public class Settings implements Serializable {

	public int noOfPlayers;
	public Player[] players;

	Settings(int _noOfplayers) {
		this.noOfPlayers = _noOfplayers;
		players = new Player[noOfPlayers];
		Color[] clr=new Color[8];
		clr[0]=Color.RED;
		clr[1]=Color.YELLOW;
		clr[2]=Color.BLUE;
		clr[3]=Color.BROWN;
		clr[4]=Color.VIOLET;
		clr[5]=Color.CRIMSON;
		clr[6]=Color.HOTPINK;
		clr[7]=Color.YELLOWGREEN;
		for(int i=0;i<noOfPlayers;i++) {
			players[i]=new Player(clr[i]);
		}
	}

	public boolean isColorValid(Color colorID) {
		for (int i = 0; i < players.length; i++) {
			// System.out.println( + " " + );
			if (players[i].colorID != null) {
				if (players[i].colorID.equals(String.valueOf(colorID)) == true) {
					return false;
				}
			}
		}
		return true;
	}

}

class ColorNotValidException extends Exception {
	public ColorNotValidException(String message) {
		super(message);
	}
}
