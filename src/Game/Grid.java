package Game;

/**
 * <h1>Main Menu GUI class</h1> The program is used to play the game.
 *
 */
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Constructor;

import MainMenu.MainMenuGUI;
import Settings.Player;
import Status.GameGUIStatus;
import Status.GridStatus;
import javafx.animation.Animation;
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

/**
 * This class throws an exception if a player attempts an invalid
 * move .
 *
 */
class InvalidMoveException extends Exception {
	InvalidMoveException(String msg) {
		super(msg);
	}
}

/**
 * This is the grid class which extends grid pane. It is used to make grid in
 * which game is to be played. Moreover the game is played in this class only.
 *
 */
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
	int nextColorTurn = 0;
	public GameGUIStatus gsundo = null;

	/**
	 * This is the constructor for Grid class. In it we initialize grid with an
	 * 2D array of cells.
	 * 
	 * @param x
	 *            width of the grid.
	 * @param y
	 *            height of the grid.
	 * @param players
	 *            array of players playing the game.
	 * @param count
	 *            integer for passing turn to players
	 */
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
				actionListener(i, j, y, x, players);
				this.add(grid[i][j], i, j);
			}
		}
		changeColor(players[nextColorTurn].getColor());

	}

	/**
	 * This Constructor of grid is used during undo and resume.
	 * 
	 * @param _grid
	 *            Object of GridStatus class which stores status of grid at any
	 *            point of time.
	 * @param players
	 *            array of players playing the game.
	 */
	public Grid(GridStatus _grid, Player[] players) {
		super();
		this.height = _grid.width;
		this.width = _grid.height;
		int y = height;
		int x = width;
		counter = _grid.count;
		nextColorTurn = _grid.count;
		int tr = counter;
		grid = new Cell[width][height];
		gridst = new GameGUIStatus(players.length, players, y + "X" + x, _grid);
		gridundo = new GameGUIStatus(players.length, players, y + "X" + x, new GridStatus(x, y, counter));
		checkClr(players);
		if (players[nextColorTurn] == null) {
			while (players[nextColorTurn] == null) {
				nextColorTurn++;
				checkClr(players);
			}
		}
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				int temp1 = i;
				int temp2 = j;
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
					grid[i][j].setP(_grid.gridSt[i][j].currentOwner);
					animation1(i, j, null, _grid.gridSt[i][j].currentOwner);
				}

				else if (_grid.gridSt[i][j].noOfOrbs == 2) {
					grid[i][j].setOrbs(2);
					grid[i][j].setP(_grid.gridSt[i][j].currentOwner);
					animation2(i, j, null, _grid.gridSt[i][j].currentOwner);
				} else if (_grid.gridSt[i][j].noOfOrbs == 3) {
					grid[i][j].setOrbs(3);
					grid[i][j].setP(_grid.gridSt[i][j].currentOwner);
					animation3(i, j, null, _grid.gridSt[i][j].currentOwner);
				}
				actionListener(i, j, y, x, players);
				this.add(grid[i][j], i, j);
			}
		}

		changeColor(players[nextColorTurn].getColor());
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

	/**
	 * This function handles click on cells with critical mass as 2. Do so by
	 * placing an orb or by splitting it.
	 * 
	 * @param x
	 *            coordinate of cell along the x axis.
	 * @param y
	 *            coordinate of cell along the y axis.
	 * @param players
	 *            array of players playing the game.
	 */
	public void OrbsEvent1(int x, int y, Player[] players) {
		counter++;
		checkCounter(players);
		nextColorTurn++;
		checkClr(players);
		if (players[counter - 1] == null) {
			while (players[counter - 1] == null) {
				counter++;
				checkCounter(players);
			}
		}
		if (grid[x][y].getOrbs() == 0) {
			animation1(x, y, players, null);
			saveState();
			players[counter - 1].setCells(players[counter - 1].getCells() + 1);
			this.grid[x][y].setP(players[counter - 1]);
			if (players[nextColorTurn] == null) {
				while (players[nextColorTurn] == null) {
					nextColorTurn++;
					checkClr(players);
				}
			}
			changeColor(players[nextColorTurn].getColor());
			checkCounter(players);
			grid[x][y].setOrbs(grid[x][y].getOrbs() + 1);
			gridst.grid.setnumber(x, y, grid[x][y].getOrbs());
			gridst.grid.setowner(x, y, grid[x][y].getP());

		} else {
			boolean checkFlag = false;
			try {
				checkMove(x, y, players);
			} catch (InvalidMoveException e) {
				checkFlag = true;
				counter--;
				nextColorTurn--;
			}
			if (checkFlag == false) {
				saveState();
				recursion(x, y, players);
			}
		}
	}

	/**
	 * This function handles click on cells with critical mass as 3. Do so by
	 * placing orbs or by splitting them.
	 * 
	 * @param x
	 *            coordinate of cell along the x axis.
	 * @param y
	 *            coordinate of cell along the y axis.
	 * @param players
	 *            array of players playing the game.
	 */
	public void OrbsEvent2(int x, int y, Player[] players) {
		counter++;
		checkCounter(players);
		nextColorTurn++;
		checkClr(players);
		if (players[counter - 1] == null) {
			while (players[counter - 1] == null) {
				counter++;
				checkCounter(players);
			}
		}
		if (grid[x][y].getOrbs() == 0) {
			animation1(x, y, players, null);
			saveState();
			players[counter - 1].setCells(players[counter - 1].getCells() + 1);
			this.grid[x][y].setP(players[counter - 1]);
			if (players[nextColorTurn] == null) {
				while (players[nextColorTurn] == null) {
					nextColorTurn++;
					checkClr(players);
				}
			}
			changeColor(players[nextColorTurn].getColor());
			checkCounter(players);
			grid[x][y].setOrbs(grid[x][y].getOrbs() + 1);
			gridst.grid.setnumber(x, y, grid[x][y].getOrbs());
			gridst.grid.setowner(x, y, grid[x][y].getP());
		} else if (grid[x][y].getOrbs() == 1) {
			boolean checkFlag = false;
			try {
				checkMove(x, y, players);
			} catch (InvalidMoveException e) {
				checkFlag = true;
				counter--;
				nextColorTurn--;
			}
			if (checkFlag == false) {
				saveState();
				grid[x][y].setOrbs(grid[x][y].getOrbs() + 1);
				if (players[nextColorTurn] == null) {
					while (players[nextColorTurn] == null) {
						nextColorTurn++;
						checkClr(players);
					}
				}
				changeColor(players[nextColorTurn].getColor());
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
			} catch (InvalidMoveException e) {
				checkFlag = true;
				counter--;
				nextColorTurn--;
			}
			if (checkFlag == false) {
				saveState();
				recursion(x, y, players);
			}
		}
	}

	/**
	 * This function handles click on cells with critical mass as 4. Do so by
	 * placing orbs or by splitting them.
	 * 
	 * @param x
	 *            coordinate of cell along the x axis.
	 * @param y
	 *            coordinate of cell along the y axis.
	 * @param players
	 *            array of players playing the game.
	 */

	public void OrbsEvent3(int x, int y, Player[] players) {
		counter++;
		checkCounter(players);
		nextColorTurn++;
		checkClr(players);
		if (players[counter - 1] == null) {
			while (players[counter - 1] == null) {
				counter++;
				checkCounter(players);
			}
		}
		if (grid[x][y].getOrbs() == 0) {
			animation1(x, y, players, null);
			saveState();
			players[counter - 1].setCells(players[counter - 1].getCells() + 1);
			this.grid[x][y].setP(players[counter - 1]);
			if (players[nextColorTurn] == null) {
				while (players[nextColorTurn] == null) {
					nextColorTurn++;
					checkClr(players);
				}
			}
			changeColor(players[nextColorTurn].getColor());
			checkCounter(players);
			this.grid[x][y].setOrbs(grid[x][y].getOrbs() + 1);
			gridst.grid.setnumber(x, y, grid[x][y].getOrbs());
			gridst.grid.setowner(x, y, grid[x][y].getP());
		} else if (grid[x][y].getOrbs() == 1) {
			boolean checkFlag = false;
			try {
				checkMove(x, y, players);
			} catch (InvalidMoveException e) {
				checkFlag = true;
				counter--;
				nextColorTurn--;
			}
			if (checkFlag == false) {
				animation2(x, y, players, null);
				saveState();
				this.grid[x][y].setP(players[counter - 1]);
				if (players[nextColorTurn] == null) {
					while (players[nextColorTurn] == null) {
						nextColorTurn++;
						checkClr(players);
					}
				}
				changeColor(players[nextColorTurn].getColor());
				checkCounter(players);
				this.grid[x][y].setOrbs(grid[x][y].getOrbs() + 1);
				gridst.grid.setnumber(x, y, grid[x][y].getOrbs());
				gridst.grid.setowner(x, y, grid[x][y].getP());
			}
		} else if (grid[x][y].getOrbs() == 2) {
			boolean checkFlag = false;
			try {
				checkMove(x, y, players);
			} catch (InvalidMoveException e) {
				checkFlag = true;
				counter--;
				nextColorTurn--;
			}
			if (checkFlag == false) {
				animation3(x, y, players, null);
				saveState();
				this.grid[x][y].setP(players[counter - 1]);
				if (players[nextColorTurn] == null) {
					while (players[nextColorTurn] == null) {
						nextColorTurn++;
						checkClr(players);
					}
				}
				changeColor(players[nextColorTurn].getColor());
				checkCounter(players);
				this.grid[x][y].setOrbs(grid[x][y].getOrbs() + 1);
				gridst.grid.setnumber(x, y, grid[x][y].getOrbs());
				gridst.grid.setowner(x, y, grid[x][y].getP());
			}
		} else {
			boolean checkFlag = false;
			try {
				checkMove(x, y, players);
			} catch (InvalidMoveException e) {
				checkFlag = true;
				counter--;
				nextColorTurn--;
			}
			if (checkFlag == false) {
				saveState();
				recursion(x, y, players);
			}
		}

	}

	/**
	 * This function handles the splitting of orbs.Its an recursive algorithm
	 * which splits orbs and then recursively calls itself at places where new
	 * orbs have been placed if there is a chance of splitting there also. It is
	 * also responsible for transition animation while splitting of orbs.
	 * 
	 * @param x
	 *            coordinate of cell along the x axis.
	 * @param y
	 *            coordinate of cell along the y axis.
	 * @param players
	 *            array of players playing the game.
	 */
	public void recursion(int x, int y, Player[] players) {
		if (winner == false) {
			disable();
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
			pt.statusProperty().addListener((abcd, abc, ab) -> {
				if (ab == Animation.Status.RUNNING) {
					disable();
				}
				if (ab == Animation.Status.STOPPED) {
					enable();
					if (players[nextColorTurn] == null) {
						while (players[nextColorTurn] == null) {
							nextColorTurn++;
							checkClr(players);
						}
					}
					changeColor(players[nextColorTurn].getColor());
				}
			});
			pt.play();
			this.grid[x][y].setGraphic(g);
			pt.setOnFinished(e -> {
				if (winner == false) {
					int n = grid[x][y].getOrbs() - (grid[x][y].getCriticalMass());
					if (n <= 0) {
						grid[x][y].setOrbs(0);
						grid[x][y].setGraphic(null);
						grid[x][y].getP().setCells(grid[x][y].getP().getCells() - 1);
						grid[x][y].setP(null);
						gridst.grid.setnumber(x, y, grid[x][y].getOrbs());
					}
					if (n == 1) {
						grid[x][y].setOrbs(1);
						gridst.grid.setnumber(x, y, grid[x][y].getOrbs());
						animation1(x, y, players, null);
					}
					boolean condition1 = false;
					boolean condition2 = false;
					boolean condition3 = false;
					boolean condition4 = false;
					if (x > 0) {
						place(x - 1, y, players);
						if (grid[x - 1][y].getOrbs() == grid[x - 1][y].getCriticalMass()) {
							condition1 = true;
						}
					}
					if (x < width - 1) {
						place(x + 1, y, players);
						if (grid[x + 1][y].getOrbs() == grid[x + 1][y].getCriticalMass()) {
							condition2 = true;
						}
					}
					if (y > 0) {
						place(x, y - 1, players);
						if (grid[x][y - 1].getOrbs() == grid[x][y - 1].getCriticalMass()) {
							condition3 = true;
						}
					}
					if (y < height - 1) {
						place(x, y + 1, players);
						if (grid[x][y + 1].getOrbs() == grid[x][y + 1].getCriticalMass()) {
							condition4 = true;
						}
					}
					boolean gameCheck = checkPlayers(players);
					if (gameCheck == false) {
						if (round == false) {
							winner = true;
							displayWinner();
						}
					}
					if (players[nextColorTurn] == null) {
						while (players[nextColorTurn] == null) {
							nextColorTurn++;
							checkClr(players);
						}
					}
					changeColor(players[nextColorTurn].getColor());
					if (winner == false) {
						if (condition1 == true) {
							recursion(x - 1, y, players);
						}
						if (condition2 == true) {
							recursion(x + 1, y, players);
						}
						if (condition3 == true) {
							recursion(x, y - 1, players);
						}
						if (condition4 == true) {
							recursion(x, y + 1, players);
						}
					}
				}

			});
		}
	}

	/**
	 * This function places an atom of the player whom is in turn at that point
	 * of time
	 * 
	 * @param x
	 *            coordinate of cell along the x axis.
	 * @param y
	 *            coordinate of cell along the y axis.
	 * @param players
	 *            array of players playing the game.
	 */
	public void place(int x, int y, Player[] players) {
		if (grid[x][y].getP() != null) {
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
		}
	}

	/**
	 * This function attaches action listener to a cell present in the grid.
	 * This function is called for every cell of the grid.
	 * 
	 * @param i
	 *            coordinate along the x axis of the cell.
	 * @param j
	 *            coordinate along the y axis of the cell.
	 * @param y
	 *            height of the grid.
	 * @param x
	 *            width of the grid.
	 * @param players
	 *            array of players playing the game.
	 */
	public void actionListener(int i, int j, int y, int x, Player[] players) {
		int temp1 = i;
		int temp2 = j;
		if ((i == 0 && (j == 0 || j == (y - 1))) || (i == (x - 1) && (j == 0 || j == (y - 1)))) {
			grid[i][j].setOnAction(e -> OrbsEvent1(temp1, temp2, players));
			grid[i][j].setCriticalMass(2);
		} else if ((i == 0 || i == (x - 1)) || (j == 0 || j == (y - 1))) {
			grid[i][j].setOnAction(e -> OrbsEvent2(temp1, temp2, players));
			grid[i][j].setCriticalMass(3);
		} else {
			grid[i][j].setOnAction(e -> OrbsEvent3(temp1, temp2, players));
			grid[i][j].setCriticalMass(4);
		}
	}

	/**
	 * This function check and eliminate players. It also conveys the message
	 * containing information that game is finished or not through returning a
	 * boolean value.
	 * 
	 * @param players
	 *            array of players playing the game.
	 * @return flag boolean value,tells whether game is finished or not.
	 */
	public boolean checkPlayers(Player[] players) {
		int count = 0;
		boolean flag = true;
		for (int i = 0; i < players.length; i++) {
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
					break;
				}
			}
		}
		return flag;
	}

	/**
	 * This function is responsible for animation on a group of single orb.
	 * 
	 * @param x
	 *            coordinate of cell along the x axis.
	 * @param y
	 *            coordinate of cell along the y axis.
	 * @param players
	 *            array of players playing the game.
	 */

	public void animation1(int x, int y, Player[] players, Player cs) {
		Group g = new Group();
		Sphere s = new Sphere();
		s.setRadius(12.0);
		s.setTranslateX(0);
		s.setTranslateY(0);
		PhongMaterial pm = new PhongMaterial();
		if (players != null) {
			pm.setDiffuseColor(players[counter - 1].getColor());
		} else {
			pm.setDiffuseColor(cs.getColor());
		}
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

	/**
	 * This function is responsible for animation on a group of 2 orbs.
	 * 
	 * @param x
	 *            coordinate of cell along the x axis.
	 * @param y
	 *            coordinate of cell along the y axis.
	 * @param players
	 *            array of players playing the game.
	 */
	public void animation2(int x, int y, Player[] players, Player cs) {
		Group g = new Group();
		PhongMaterial pm = new PhongMaterial();
		if (players != null)
			pm.setDiffuseColor(players[counter - 1].getColor());
		else
			pm.setDiffuseColor(cs.getColor());
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

	/**
	 * This function is responsible for animation on a group of three orbs.
	 * 
	 * @param x
	 *            coordinate of cell along the x axis.
	 * @param y
	 *            coordinate of cell along the y axis.
	 * @param players
	 *            array of players playing the game.
	 */
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

	/**
	 * This function disables click on the grid.Used while transition animation
	 * is been played.
	 */

	public void disable() {
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				grid[i][j].setDisable(true);
			}
		}
	}

	/**
	 * This function is used to enable click on the grid after transition
	 * animation is completed.
	 */
	public void enable() {
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				grid[i][j].setDisable(false);
			}
		}
	}

	/**
	 * This function deals with the counter which is responsible for turn of
	 * each player. It changes the counter to point to the first player after
	 * the turn of last player.
	 * 
	 * @param players
	 *            array of players playing the game.
	 */

	public void checkCounter(Player[] players) {

		if (counter > players.length) {
			counter = 1;
		}
		gridst.grid.count = counter;
	}

	/**
	 * This function deals with the nextColorTurn which is responsible for
	 * changing the color of the grid to the color of the player who has the
	 * turn. It changes the nextColorTurn to point to the first player after
	 * turn of the last.
	 * 
	 * @param players
	 *            array of players playing the game.
	 */

	public void checkClr(Player[] players) {

		if (nextColorTurn >= players.length) {
			nextColorTurn = 0;
		}
		gridst.grid.turn = nextColorTurn;
	}

	/**
	 * This function checks for invalid move and throws InvalidMoveException. It
	 * does so by checking the current player assigned to the orb and comparing
	 * it with the player in turn.
	 * 
	 * @param x
	 *            coordinate of cell along the x axis.
	 * @param y
	 *            coordinate of cell along the y axis.
	 * @param players
	 *            array of players playing the game.
	 */

	public void checkMove(int x, int y, Player[] players) throws InvalidMoveException {
		if (!grid[x][y].getP().equals(players[counter - 1])) {
			throw new InvalidMoveException("exception");
		}
	}

	/**
	 * This function saves state of grid after each turn.
	 * 
	 * @return GameGUIStatus object to store Game Status
	 */
	public GameGUIStatus saveState() {
		gridundo = gridst;
		try {
			GameGUIStatus.serialize("GameUndo", gridundo);
			try {
				gsundo = GameGUIStatus.deserialize("GameUndo");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return gsundo;
	}

	/**
	 * This function returns object of gridStatus class. It is used in undo
	 * functionality.
	 * 
	 * @return gsundo object of the gridStatus class.
	 */
	public GameGUIStatus ReturnUndo() {
		return gsundo;
	}

	/**
	 * This function changes the color of the grid. It is passed with color as
	 * the parameter with help of nextColorTurn and then through traversing in
	 * the grid,color is changed.
	 * 
	 * @param color
	 */
	public void changeColor(Color color) {
		String col = String.valueOf(color);
		char[] arr = col.toCharArray();
		String colore = "#";
		for (int i = 2; i < arr.length - 2; i++) {
			colore = colore + arr[i];
		}
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				grid[i][j].setStyle("-fx-border-color: " + colore + ";");
			}
		}
	}

	/**
	 * This function is responsible for showing an alert displaying the winner
	 * and it also helps in stopping all the redundant recursive calls after
	 * game is finished by changing a flag.
	 * 
	 */
	public void displayWinner() {
		round = true;
		winner = true;
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Game Ends");
		alert.setHeaderText("Player " + (counter) + " wins");
		alert.setContentText("Play again");
		alert.setOnHidden(evt -> {
			try {
				gridst.grid.winner = true;
				GameGUIStatus.serialize("GamePlay", gridst);
				getScene().getWindow().hide();
				new MainMenuGUI().start(new Stage());
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		alert.show();

	}

}
