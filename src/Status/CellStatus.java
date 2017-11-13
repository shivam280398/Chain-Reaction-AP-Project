package Status;

import java.io.Serializable;

import Settings.Player;

public class CellStatus implements Serializable{
	
	public int noOfOrbs;
	public Player currentOwner;
	
	
	public CellStatus(int _n,Player _player){
		noOfOrbs = _n;
		currentOwner = _player;
	}
	
}
