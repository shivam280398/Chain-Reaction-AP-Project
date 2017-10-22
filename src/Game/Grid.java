package Game;

import javafx.animation.RotateTransition;
import javafx.event.ActionEvent;
import javafx.scene.Group;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Sphere;
import javafx.util.Duration;

public class Grid extends GridPane {
	public Cell[][] grid;
	private int height;
	private int width;
	private DropShadow shadow = new DropShadow();

	public Grid(int x, int y) {
		super();
		this.height = x;
		this.width = y;
		grid = new Cell[x][y];
		for (int i = 0; i < x; i++) {
			for (int j = 0; j < y; j++) {
				int temp1 = i;
				int temp2 = j;
				grid[i][j] = new Cell(i, j);
				grid[i][j].setEffect(shadow);
				if(x == 6 && x == 9)
				{grid[i][j].setMinSize(100, 100);
				grid[i][j].setMaxSize(100, 100);}
				else 
					grid[i][j].setMinSize(20, 20);
				//grid[i][j].setMaxSize(50, 50);
				if ((i == 0 && (j == 0 || j == (y - 1))) || (i == (x - 1) && (j == 0 || j == (y - 1)))) {
					grid[i][j].setOnAction(e -> OrbsEvent1(temp1, temp2));
				} else if ((i == 0 || i == (x - 1)) || (j == 0 || j == (y - 1))) {
					grid[i][j].setOnAction(e -> OrbsEvent2(temp1, temp2));
				} else {
					grid[i][j].setOnAction(e -> OrbsEvent3(temp1, temp2));
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

	public void OrbsEvent1(int x, int y) {
		grid[x][y].setOrbs(grid[x][y].getOrbs() + 1);
		if (grid[x][y].getOrbs() == 1) {
			Sphere s = new Sphere();
			s.setRadius(2.0);
			s.setTranslateX(0);
			s.setTranslateY(0);
			s.setStyle("-fx-background-color: #28cc28;");
			// String s="assets/sphere.png";
			// Image image=new Image(getClass().getResourceAsStream("sphere.png"));
			// Image image =new Image(s);
			// ImageView imageView=new ImageView();
			// imageView.setImage(image);
			RotateTransition rotateTransition = new RotateTransition();
			rotateTransition.setDuration(Duration.millis(1000));
			rotateTransition.setNode(s);
			rotateTransition.setByAngle(360);
			rotateTransition.setCycleCount(50);
			rotateTransition.setAutoReverse(false);
			rotateTransition.play();
			this.grid[x][y].setGraphic(s);
		} else {
			System.out.println("Split");
			this.grid[x][y].setGraphic(null);
			grid[x][y].setOrbs(0);
		}
	}

	public void OrbsEvent2(int x, int y) {
		grid[x][y].setOrbs(grid[x][y].getOrbs() + 1);
		if (grid[x][y].getOrbs() == 1) {
			Sphere s = new Sphere();
			s.setRadius(1.0);
			s.setTranslateX(0);
			s.setTranslateY(0);
			this.grid[x][y].setGraphic(s);
		} else if (grid[x][y].getOrbs() == 2) {
			Group g = new Group();
			Sphere s = new Sphere();
			s.setRadius(20.0);
			s.setTranslateX(0);
			s.setTranslateY(-4);
			Sphere t = new Sphere();
			t.setRadius(20.0);
			t.setTranslateX(0);
			t.setTranslateY(8);
			g.getChildren().add(s);
			g.getChildren().add(t);
			this.grid[x][y].setGraphic(g);
		} else {
			System.out.println("Split");
			this.grid[x][y].setGraphic(null);
			grid[x][y].setOrbs(0);
		}
	}

	public void OrbsEvent3(int x, int y) {
		grid[x][y].setOrbs(grid[x][y].getOrbs() + 1);
		if (grid[x][y].getOrbs() == 1) {
			Sphere s = new Sphere();
			s.setRadius(5.0);
			s.setTranslateX(0);
			s.setTranslateY(0);
			this.grid[x][y].setGraphic(s);
		} else if (grid[x][y].getOrbs() == 2) {
			Group g = new Group();
			Sphere s = new Sphere();
			s.setRadius(20.0);
			s.setTranslateX(0);
			s.setTranslateY(-4);
			Sphere t = new Sphere();
			t.setRadius(20.0);
			t.setTranslateX(0);
			t.setTranslateY(8);
			g.getChildren().add(s);
			g.getChildren().add(t);
			this.grid[x][y].setGraphic(g);
		} else if (grid[x][y].getOrbs() == 3) {
			Group g = new Group();
			Sphere s = new Sphere();
			s.setRadius(20.0);
			s.setTranslateX(-8);
			s.setTranslateY(-4);
			Sphere t = new Sphere();
			t.setRadius(20.0);
			t.setTranslateX(-8);
			t.setTranslateY(15);
			Sphere z = new Sphere();
			z.setRadius(20.0);
			z.setTranslateX(16);
			z.setTranslateY(4);
			g.getChildren().add(s);
			g.getChildren().add(z);
			g.getChildren().add(t);
			this.grid[x][y].setGraphic(g);
		} else {
			System.out.println("Split");
			this.grid[x][y].setGraphic(null);
			grid[x][y].setOrbs(0);
		}

	}
}
