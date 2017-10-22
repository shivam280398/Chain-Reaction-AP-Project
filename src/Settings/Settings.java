package Settings;

import java.util.ArrayList;

import javafx.scene.paint.Color;

public class Settings {

	
	int noOfPlayers;
	 Player[] players;
	
	Settings( ){
		this.noOfPlayers = 8;
		players = new Player[8];
		for(int i=0;i<8;i++){
			players[i] = new Player();
		}
	}
	
	public boolean isColorValid(Color colorID){
		for(int i=0;i<players.length;i++){
			if(players[i].getColor() == colorID){
				return false;
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
