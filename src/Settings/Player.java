package Settings;

import javafx.scene.paint.Color;

public class Player {

	Color colorID;
	
	Player(){
		colorID = null;
	}
	
	Player(Color _colorID){
		this.colorID = _colorID;
	}
	
	public Color getColor(){
		return colorID;
	}
	
	public void setColor(Color _colorID){
		this.colorID = _colorID;
	}
	
}
