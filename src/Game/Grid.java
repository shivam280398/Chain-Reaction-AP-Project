package Game;

import java.io.FileNotFoundException;
import java.io.IOException;

import MainMenu.MainMenuGUI;
import Settings.Player;
import Status.CellStatus;
import Status.GameGUIStatus;
import Status.GridStatus;
import javafx.animation.Interpolator;
import javafx.animation.ParallelTransition;
import javafx.animation.RotateTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;
import javafx.stage.Stage;
import javafx.util.Duration;

class invalid extends Exception {
	invalid(String msg) {
		super(msg);
	}
}

public class Grid extends GridPane {
	public Cell[][] grid;
	public GameGUIStatus gridst;
	public GameGUIStatus gridundo;
	private int height;
	private int width;
	private DropShadow shadow = new DropShadow();
	public boolean winner = false;
	private boolean round = false;
	int counter;
	public GameGUIStatus gsundo = null;

	public Grid(int x, int y, Player[] players, int count) {
		super();
		counter = count;
		this.height = y;
		this.width = x;
		grid = new Cell[x][y];
		gridst = new GameGUIStatus(players.length, players, y + "X" + x, new GridStatus(x, y, counter));
		gridundo = new GameGUIStatus(players.length, players, y + "X" + x, new GridStatus(x, y, counter));
		for (int i = 0; i < x; i++) {
			for (int j = 0; j < y; j++) {
				int temp1 = i;
				int temp2 = j;
				grid[i][j] = new Cell(i, j);
				grid[i][j].setEffect(shadow);
				if (x == 10 && y == 15) {
					grid[i][j].setMinSize(45, 45);
					grid[i][j].setMaxSize(45, 45);

				} else {
					grid[i][j].setMinSize(60, 60);
					grid[i][j].setMaxSize(60, 60);
				}
				if ((i == 0 && (j == 0 || j == (y - 1))) || (i == (x - 1) && (j == 0 || j == (y - 1)))) {
					grid[i][j].setOnAction(e -> OrbsEvent1(temp1, temp2, players));
					grid[i][j].setCriticalMass(1);
				} else if ((i == 0 || i == (x - 1)) || (j == 0 || j == (y - 1))) {
					grid[i][j].setOnAction(e -> OrbsEvent2(temp1, temp2, players));
					grid[i][j].setCriticalMass(2);
				} else {
					grid[i][j].setOnAction(e -> OrbsEvent3(temp1, temp2, players));
					grid[i][j].setCriticalMass(3);
				}
				this.add(grid[i][j], i, j);
			}
		}
		
	}

	public Grid(GridStatus _grid, Player[] players) {
		// TODO Auto-generated constructor stub
		super();
		this.height = _grid.width;
		this.width = _grid.height;
		int y = height;
		int x = width;
		_grid.print();
		counter = _grid.count;
		System.out.println(counter);
		grid = new Cell[width][height];
		System.out.println(height + " " + width);
		gridst = new GameGUIStatus(players.length, players, y + "X" + x, _grid);
		gridundo = new GameGUIStatus(players.length, players, y + "X" + x, new GridStatus(x, y, counter));
		// saveState(1);

		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				int temp1 = i;
				int temp2 = j;
				System.out.println(i + "  " + j);
				grid[i][j] = new Cell(i, j);
				grid[i][j].setEffect(shadow);
				if (width == 10 && height == 15) {
					grid[i][j].setMinSize(45, 45);
					grid[i][j].setMaxSize(45, 45);

				} else {
					grid[i][j].setMinSize(60, 60);
					grid[i][j].setMaxSize(60, 60);
				}
				if (_grid.gridSt[i][j].noOfOrbs == 1) {
					grid[i][j].setOrbs(1);

					System.out.println(_grid.gridSt[i][j].currentOwner.getColor());
					grid[i][j].setP(_grid.gridSt[i][j].currentOwner);
					animation1(i, j, null, _grid.gridSt[i][j].currentOwner);
				}

				else if (_grid.gridSt[i][j].noOfOrbs == 2) {
					grid[i][j].setOrbs(2);
					System.out.println(_grid.gridSt[i][j].currentOwner.getColor());

					grid[i][j].setP(_grid.gridSt[i][j].currentOwner);
					animation2(i, j, null, _grid.gridSt[i][j].currentOwner);
				} else if (_grid.gridSt[i][j].noOfOrbs == 3) {
					grid[i][j].setOrbs(3);
					System.out.println(_grid.gridSt[i][j].currentOwner.getColor());

					grid[i][j].setP(_grid.gridSt[i][j].currentOwner);
					animation3(i, j, null, _grid.gridSt[i][j].currentOwner);
				}

				if ((i == 0 && (j == 0 || j == (y - 1))) || (i == (x - 1) && (j == 0 || j == (y - 1)))) {
					grid[i][j].setOnAction(e -> OrbsEvent1(temp1, temp2, players));
					grid[i][j].setCriticalMass(1);
				} else if ((i == 0 || i == (x - 1)) || (j == 0 || j == (y - 1))) {
					grid[i][j].setOnAction(e -> OrbsEvent2(temp1, temp2, players));
					grid[i][j].setCriticalMass(2);
				} else {
					grid[i][j].setOnAction(e -> OrbsEvent3(temp1, temp2, players));
					grid[i][j].setCriticalMass(3);
				}

				this.add(grid[i][j], i, j);
			}
		}
	}

	public Cell[][] getGrid() {
		return grid;
	}

	public void setGrid(Cell[][] grid) {
		this.grid = grid;
	}

	public int getheight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getwidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public void OrbsEvent1(int x, int y, Player[] players) {
		counter++;
		checkCounter(players);
		if (players[counter - 1] == null) {
			while (players[counter - 1] == null) {
				counter++;
				checkCounter(players);
			}
		}
		if (grid[x][y].getOrbs() == 0) {
			animation1(x, y, players, null);
			saveState(1);
			players[counter - 1].setCells(players[counter - 1].getCells() + 1);
			this.grid[x][y].setP(players[counter - 1]);
			checkCounter(players);
			grid[x][y].setOrbs(grid[x][y].getOrbs() + 1);
			gridst.grid.setnumber(x, y, grid[x][y].getOrbs());
			gridst.grid.setowner(x, y, grid[x][y].getP());

		} else {
			boolean checkFlag = false;
			try {
				checkMove(x, y, players);
			} catch (invalid e) {
				checkFlag = true;
				counter--;
			}
			if (checkFlag == false) {
				saveState(1);
				recursion(x, y, players);
			}
		}
	}

	public void OrbsEvent2(int x, int y, Player[] players) {
		counter++;
		checkCounter(players);
		if (players[counter - 1] == null) {
			while (players[counter - 1] == null) {
				counter++;
				checkCounter(players);
			}
		}
		if (grid[x][y].getOrbs() == 0) {
			animation1(x, y, players, null);
			saveState(1);
			players[counter - 1].setCells(players[counter - 1].getCells() + 1);
			this.grid[x][y].setP(players[counter - 1]);
			checkCounter(players);
			grid[x][y].setOrbs(grid[x][y].getOrbs() + 1);

			gridst.grid.setnumber(x, y, grid[x][y].getOrbs());
			gridst.grid.setowner(x, y, grid[x][y].getP());
		} else if (grid[x][y].getOrbs() == 1) {
			boolean checkFlag = false;
			try {
				checkMove(x, y, players);
			} catch (invalid e) {
				checkFlag = true;
				counter--;
			}
			if (checkFlag == false) {
				saveState(1);
				grid[x][y].setOrbs(grid[x][y].getOrbs() + 1);
				animation2(x, y, players, null);
				this.grid[x][y].setP(players[counter - 1]);

				gridst.grid.setnumber(x, y, grid[x][y].getOrbs());
				gridst.grid.setowner(x, y, grid[x][y].getP());
				checkCounter(players);
			}
		} else {
			boolean checkFlag = false;
			try {
				checkMove(x, y, players);
			} catch (invalid e) {
				checkFlag = true;
				counter--;
			}
			if (checkFlag == false) {
				saveState(1);
				recursion(x, y, players);
			}
		}
	}

	public void OrbsEvent3(int x, int y, Player[] players) {
		counter++;
		System.out.println(counter - 1);
		checkCounter(players);
		if (players[counter - 1] == null) {
			while (players[counter - 1] == null) {
				counter++;
				checkCounter(players);
			}
		}
		if (grid[x][y].getOrbs() == 0) {
			animation1(x, y, players, null);
			saveState(1);
			players[counter - 1].setCells(players[counter - 1].getCells() + 1);
			this.grid[x][y].setP(players[counter - 1]);
			checkCounter(players);
			this.grid[x][y].setOrbs(grid[x][y].getOrbs() + 1);

			gridst.grid.setnumber(x, y, grid[x][y].getOrbs());
			gridst.grid.setowner(x, y, grid[x][y].getP());
			print();
		} else if (grid[x][y].getOrbs() == 1) {
			boolean checkFlag = false;
			try {
				checkMove(x, y, players);
			} catch (invalid e) {
				checkFlag = true;
				counter--;
			}
			if (checkFlag == false) {
				animation2(x, y, players, null);
				saveState(1);
				this.grid[x][y].setP(players[counter - 1]);
				checkCounter(players);
				this.grid[x][y].setOrbs(grid[x][y].getOrbs() + 1);

				gridst.grid.setnumber(x, y, grid[x][y].getOrbs());
				gridst.grid.setowner(x, y, grid[x][y].getP());
				print();
			}
		} else if (grid[x][y].getOrbs() == 2) {
			boolean checkFlag = false;
			try {
				checkMove(x, y, players);
			} catch (invalid e) {
				checkFlag = true;
				counter--;
			}
			if (checkFlag == false) {
				animation3(x, y, players, null);
				saveState(1);
				this.grid[x][y].setP(players[counter - 1]);
				checkCounter(players);
				this.grid[x][y].setOrbs(grid[x][y].getOrbs() + 1);

				gridst.grid.setnumber(x, y, grid[x][y].getOrbs());
				gridst.grid.setowner(x, y, grid[x][y].getP());
				print();
			}
		} else {
			boolean checkFlag = false;
			try {
				checkMove(x, y, players);
			} catch (invalid e) {
				checkFlag = true;
				counter--;
			}
			if (checkFlag == false) {
				saveState(1);
				recursion(x, y, players);
			}
		}

	}

	public void recursion(int x, int y, Player[] players) {
		grid[x][y].setOrbs(0);
		grid[x][y].setGraphic(null);
		// saveState(1);
		grid[x][y].getP().setCells(grid[x][y].getP().getCells() - 1);
		grid[x][y].setP(null);
		gridst.grid.setnumber(x, y, grid[x][y].getOrbs());
		gridst.grid.setowner(x, y, grid[x][y].getP());
		Sphere s1 = new Sphere();
		Sphere s2 = new Sphere();
		Sphere s3 = new Sphere();
		Sphere s4 = new Sphere();
		PhongMaterial pm = new PhongMaterial();
		pm.setDiffuseColor(players[counter - 1].getColor());
		
		s1.setMaterial(pm);
		s2.setMaterial(pm);
		s3.setMaterial(pm);
		s4.setMaterial(pm);
		s1.setRadius(13);
		s2.setRadius(13);
		s3.setRadius(13);
		s4.setRadius(13);
		TranslateTransition tTransition1 = new TranslateTransition();
		TranslateTransition tTransition2 = new TranslateTransition();
		TranslateTransition tTransition3 = new TranslateTransition();
		TranslateTransition tTransition4 = new TranslateTransition();
		tTransition1.setDuration(Duration.millis(300));
		tTransition1.setNode(s1);
		tTransition1.setByX(-20);
		tTransition1.setCycleCount(1);
		tTransition1.setAutoReverse(false);
		tTransition2.setDuration(Duration.millis(300));
		tTransition2.setNode(s2);
		tTransition2.setByX(20);
		tTransition2.setCycleCount(1);
		tTransition2.setAutoReverse(false);
		tTransition3.setDuration(Duration.millis(300));
		tTransition3.setNode(s3);
		tTransition3.setByY(-20);
		tTransition3.setCycleCount(1);
		tTransition3.setAutoReverse(false);
		tTransition4.setDuration(Duration.millis(300));
		tTransition4.setNode(s4);
		tTransition4.setByY(20);
		tTransition2.setCycleCount(1);
		tTransition2.setAutoReverse(false);
		Group g = new Group();
		if (x > 0) {
			g.getChildren().add(s1);
		}
		if (x < width - 1) {
			g.getChildren().add(s2);
		}
		if (y > 0) {
			g.getChildren().add(s3);
		}
		if (y < height - 1) {
			g.getChildren().add(s4);
		}
		ParallelTransition pt = new ParallelTransition(tTransition1, tTransition2, tTransition3, tTransition4);
		pt.play();
		this.grid[x][y].setGraphic(g);
		pt.setOnFinished(e -> {
			this.grid[x][y].setGraphic(null);
			if (x > 0) {
				place(x - 1, y, players);
				if (grid[x - 1][y].getOrbs() > grid[x - 1][y].getCriticalMass()) {
					recursion(x - 1, y, players);
				}
			}
			if (x < width - 1) {
				place(x + 1, y, players);
				if (grid[x + 1][y].getOrbs() > grid[x + 1][y].getCriticalMass()) {
					recursion(x + 1, y, players);
				}
			}
			if (y > 0) {
				place(x, y - 1, players);
				if (grid[x][y - 1].getOrbs() > grid[x][y - 1].getCriticalMass()) {
					recursion(x, y - 1, players);
				}
			}
			if (y < height - 1) {
				place(x, y + 1, players);
				if (grid[x][y + 1].getOrbs() > grid[x][y + 1].getCriticalMass()) {
					recursion(x, y + 1, players);
				}
			}
			boolean gameCheck = checkPlayers(players);
			if (gameCheck == false) {
				if (round == false) {
					winner = true;
					displayWinner();
				}
			}
		});
	}

	public void place(int x, int y, Player[] players) {
		System.out.println(counter);
		// saveState(1);
		if (grid[x][y].getP() != null) {
			// System.out.println(grid[x][y].getP().getCells() - 1);
			grid[x][y].getP().setCells(grid[x][y].getP().getCells() - 1);
		}
		grid[x][y].setP(players[counter - 1]);
		players[counter - 1].setCells(players[counter - 1].getCells() + 1);
		grid[x][y].setOrbs(grid[x][y].getOrbs() + 1);
		gridst.grid.setnumber(x, y, grid[x][y].getOrbs());
		gridst.grid.setowner(x, y, grid[x][y].getP());
		if (grid[x][y].getOrbs() == 1) {
			animation1(x, y, players, null);
		} else if (grid[x][y].getOrbs() == 2) {
			animation2(x, y, players, null);
		} else if (grid[x][y].getOrbs() == 3) {
			animation3(x, y, players, null);
		} else {
			animation4(x, y, players);
		}
	}

	public boolean checkPlayers(Player[] players) {
		int count = 0;
		boolean flag = true;
		for (int i = 0; i < players.length; i++) {
			if (players[i] != null) {
				// System.out.println(players[i].getCells()+"heyy");
			}
			if (players[i] == null) {
				count++;
			} else if (players[i].getCells() == 0) {
				players[i] = null;
				count++;
			}
		}
		if (count == players.length - 1)

		{
			flag = false;
			for (int i = 0; i < players.length; i++) {
				if (players[i] != null) {
					// System.out.println("Player " + (i + 1) + " wins");
					break;
				}
			}
		}
		return flag;
	}

	public void animation1(int x, int y, Player[] players, Player cs) {
		Group g = new Group();
		Sphere s = new Sphere();
		s.setRadius(12.0);
		s.setTranslateX(0);
		s.setTranslateY(0);
		PhongMaterial pm = new PhongMaterial();
		if (players != null)
			pm.setDiffuseColor(players[counter - 1].getColor());
		
		else
			pm.setDiffuseColor(cs.getColor());
		changeColor(players[counter - 1].getColor());
		s.setMaterial(pm);
		g.getChildren().add(s);
		RotateTransition rotateTransition = new RotateTransition();
		rotateTransition.setDuration(Duration.millis(1000));
		rotateTransition.setNode(g);
		rotateTransition.setByAngle(360);
		rotateTransition.setCycleCount(Timeline.INDEFINITE);
		rotateTransition.setAutoReverse(false);
		rotateTransition.play();
		this.grid[x][y].setGraphic(g);
	}

	public void animation2(int x, int y, Player[] players, Player cs) {
		Group g = new Group();
		PhongMaterial pm = new PhongMaterial();
		if (players != null)
			pm.setDiffuseColor(players[counter - 1].getColor());
		else
			pm.setDiffuseColor(cs.getColor());
		changeColor(pm.getDiffuseColor());
		Sphere s = new Sphere();
		s.setRadius(12.0);
		s.setTranslateX(0);
		s.setTranslateY(-4);
		Sphere t = new Sphere();
		t.setRadius(12.0);
		t.setTranslateX(0);
		t.setTranslateY(8);
		g.getChildren().add(s);
		g.getChildren().add(t);
		s.setMaterial(pm);
		t.setMaterial(pm);
		RotateTransition rotateTransition = new RotateTransition();
		rotateTransition.setDuration(Duration.millis(1000));
		rotateTransition.setNode(g);
		rotateTransition.setByAngle(360);
		rotateTransition.setCycleCount(Timeline.INDEFINITE);
		rotateTransition.setAutoReverse(false);
		rotateTransition.setInterpolator(Interpolator.LINEAR);
		rotateTransition.play();
		this.grid[x][y].setGraphic(g);
	}

	public void animation3(int x, int y, Player[] players, Player cs) {
		Group g = new Group();
		Sphere s = new Sphere();
		s.setRadius(13.0);
		s.setTranslateX(-8);
		s.setTranslateY(-4);
		Sphere t = new Sphere();
		t.setRadius(13.0);
		t.setTranslateX(-8);
		t.setTranslateY(12);
		Sphere z = new Sphere();
		z.setRadius(13.0);
		z.setTranslateX(12);
		z.setTranslateY(4);
		PhongMaterial pm = new PhongMaterial();
		if (players != null)
			pm.setDiffuseColor(players[counter - 1].getColor());
		else
			pm.setDiffuseColor(cs.getColor());
		changeColor(pm.getDiffuseColor());
		s.setMaterial(pm);
		t.setMaterial(pm);
		z.setMaterial(pm);
		g.getChildren().add(s);
		g.getChildren().add(z);
		g.getChildren().add(t);
		RotateTransition rotateTransition = new RotateTransition();
		rotateTransition.setInterpolator(Interpolator.LINEAR);
		rotateTransition.setDuration(Duration.millis(2000));
		rotateTransition.setNode(g);
		rotateTransition.setByAngle(360);
		rotateTransition.setCycleCount(Timeline.INDEFINITE);
		rotateTransition.setAutoReverse(false);
		rotateTransition.play();
		this.grid[x][y].setGraphic(g);
	}

	public void animation4(int x, int y, Player[] players) {
		Group g = new Group();
		Sphere s = new Sphere();
		s.setRadius(13.0);
		s.setTranslateX(-8);
		s.setTranslateY(0);
		Sphere t = new Sphere();
		t.setRadius(13.0);
		t.setTranslateX(8);
		t.setTranslateY(0);
		Sphere z = new Sphere();
		z.setRadius(13.0);
		z.setTranslateX(0);
		z.setTranslateY(8);
		Sphere a = new Sphere();
		a.setRadius(13.0);
		a.setTranslateX(0);
		a.setTranslateY(-8);
		PhongMaterial pm = new PhongMaterial();
		pm.setDiffuseColor(players[counter - 1].getColor());
		s.setMaterial(pm);
		t.setMaterial(pm);
		z.setMaterial(pm);
		a.setMaterial(pm);
		g.getChildren().add(s);
		g.getChildren().add(z);
		g.getChildren().add(t);
		g.getChildren().add(a);
		RotateTransition rotateTransition = new RotateTransition();
		rotateTransition.setInterpolator(Interpolator.LINEAR);
		rotateTransition.setDuration(Duration.millis(2000));
		rotateTransition.setNode(g);
		rotateTransition.setByAngle(360);
		rotateTransition.setCycleCount(Timeline.INDEFINITE);
		rotateTransition.setAutoReverse(false);
		rotateTransition.play();
		this.grid[x][y].setGraphic(g);
	}

	public void displayWinner() {
		round = true;
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Game Ends");
		alert.setHeaderText("Player " + (counter) + " wins");
		alert.setContentText("Play again");
		alert.setOnHidden(evt -> {
			try {
				getScene().getWindow().hide();
				new MainMenuGUI().start(new Stage());
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		alert.show();

	}

	public void checkCounter(Player[] players) {

		if (counter > players.length) {
			counter = 1;
		}
		gridst.grid.count = counter;
	}

	public void checkMove(int x, int y, Player[] players) throws invalid {
		if (!grid[x][y].getP().equals(players[counter - 1])) {
			throw new invalid("exception");
		}
	}

	public GameGUIStatus saveState(int i) {
		if (i == 1) {
			// if(gridundo!=null)
			// //gridundo.print();
			// else
			// System.out.println("null");
			// gridst.print();

			gridundo = gridst;
		}
		System.out.println("Testing...");
		gridundo.grid.print();
		try {
			GameGUIStatus.serialize("GameUndo", gridundo);

			try {
				gsundo = GameGUIStatus.deserialize("GameUndo");
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// gsundo.print();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return gsundo;
	}

	public void print() {
		// gridst.print();
		// System.out.println("2");
		// gridundo.print();
		// System.out.println("3");
		// gsundo.grid.print();
	}

	public GameGUIStatus ss() {
		return gsundo;
	}
	
	public void changeColor(Color color){
		String col = String.valueOf(color);
		System.out.println(col);
		char[] arr = col.toCharArray();
		String colore = "#";
		for(int i=2;i<arr.length-2;i++){
			colore = colore + arr[i];
		}
		System.out.println(colore);
		for(int i=0;i<width;i++){
			for(int j=0;j<height;j++){
				grid[i][j].setStyle("-fx-border-color: " + colore +  ";");
			}
		}
	}

}
