package Status;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import Settings.Player;

public class GridStatus implements Serializable{
	
	public int height;
	public int width;
	public CellStatus[][] gridSt;
	public int turn;
	public int count=0;
	
	public GridStatus(int _height,int _width,int counter){
		
		this.height = _height;
		this.width = _width;
		count = counter;
		gridSt = new CellStatus[height][width];
		for(int i=0;i<_height;i++){
			for(int j = 0;j<_width;j++){
				gridSt[i][j] = new CellStatus(0,null);
			}
		}
		
	}
	
	public void setowner(int x,int y,Player p){
		gridSt[x][y].currentOwner = p;
	}
	
	public void setnumber(int x,int y,int _n){
		gridSt[x][y].noOfOrbs = _n;
	}
	
	public void updateturn(int counter){
		turn = counter;
	}
	
	public void print(){
		for(int i=0;i<width;i++){
			for(int j=0;j<height;j++){
				System.out.print(gridSt[j][i].noOfOrbs + " ");
//				if(gridSt[j][i].currentOwner!=null){
//					System.out.print(gridSt[j][i].currentOwner.getColor());
//				}
			}
			System.out.println();
		}
		System.out.println();
	}
	
	public static void serialize(String pathname, GridStatus pl) throws FileNotFoundException, IOException {
		ObjectOutputStream out = null;
		try {

			out = new ObjectOutputStream(new FileOutputStream("src/" + pathname + ".txt"));
			out.writeObject(pl);

		} finally {
			out.close();
		}
	}

	public static GridStatus deserialize(String pathname) throws IOException, ClassNotFoundException {
		ObjectInputStream in = null;
		GridStatus gameGUI = null;
		try {
			in = new ObjectInputStream(new FileInputStream("src/" + pathname + ".txt"));
			gameGUI = (GridStatus) in.readObject();

		} finally {
			in.close();
		}
		return gameGUI;
	}

}
