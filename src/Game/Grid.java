package Game;

import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.Group;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;
import javafx.util.Duration;

public class Grid extends GridPane {
	public Cell[][] grid;
	private int height;
	private int width;
	private DropShadow shadow = new DropShadow();
	private int param = 0;

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
				if (x == 10 && y == 15) {
					grid[i][j].setMinSize(45, 45);
					grid[i][j].setMaxSize(45, 45);

				} else {
					grid[i][j].setMinSize(60, 60);
					grid[i][j].setMaxSize(60, 60);
				}
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
			s.setRadius(12.0);
			s.setTranslateX(0);
			s.setTranslateY(0);
			s.setStyle("-fx-background-color: #28cc28;");
			PhongMaterial pm = new PhongMaterial();
			pm.setDiffuseColor(Color.RED);
			s.setMaterial(pm);
			RotateTransition rotateTransition = new RotateTransition();
			rotateTransition.setDuration(Duration.millis(1000));
			rotateTransition.setNode(s);
			rotateTransition.setByAngle(360);
			rotateTransition.setCycleCount(Timeline.INDEFINITE);
			rotateTransition.setAutoReverse(false);
			rotateTransition.play();
			this.grid[x][y].setGraphic(s);
		} else {
			this.grid[x][y].setGraphic(null);
			grid[x][y].setOrbs(0);
		}
	}

	public void OrbsEvent2(int x, int y) {
		grid[x][y].setOrbs(grid[x][y].getOrbs() + 1);
		if (grid[x][y].getOrbs() == 1) {
			Sphere s = new Sphere();
			s.setRadius(12.0);
			s.setTranslateX(0);
			s.setTranslateY(0);
			PhongMaterial pm = new PhongMaterial();
			pm.setDiffuseColor(Color.RED);
			s.setMaterial(pm);
			this.grid[x][y].setGraphic(s);
		} else if (grid[x][y].getOrbs() == 2) {
			Group g = new Group();
			PhongMaterial pm = new PhongMaterial();
			pm.setDiffuseColor(Color.RED);
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
		} else {
			this.grid[x][y].setGraphic(null);
			grid[x][y].setOrbs(0);
		}
	}

	public void OrbsEvent3(int x, int y) {
		grid[x][y].setOrbs(grid[x][y].getOrbs() + 1);
		if (grid[x][y].getOrbs() == 1) {
			Sphere s = new Sphere();
			s.setRadius(12.0);
			s.setTranslateX(0);
			s.setTranslateY(0);
			PhongMaterial pm = new PhongMaterial();
			pm.setDiffuseColor(Color.RED);
			s.setMaterial(pm);
			this.grid[x][y].setGraphic(s);
		} else if (grid[x][y].getOrbs() == 2) {
			Group g = new Group();
			Sphere s = new Sphere();
			s.setRadius(12.0);
			s.setTranslateX(0);
			s.setTranslateY(-4);
			Sphere t = new Sphere();
			t.setRadius(12.0);
			t.setTranslateX(0);
			t.setTranslateY(8);
			PhongMaterial pm = new PhongMaterial();
			pm.setDiffuseColor(Color.RED);
			s.setMaterial(pm);
			t.setMaterial(pm);
			g.getChildren().add(s);
			g.getChildren().add(t);
			RotateTransition rotateTransition = new RotateTransition();
			rotateTransition.setDuration(Duration.millis(1000));
			rotateTransition.setNode(g);
			rotateTransition.setByAngle(360);
			rotateTransition.setCycleCount(Timeline.INDEFINITE);
			rotateTransition.setAutoReverse(false);
			rotateTransition.setInterpolator(Interpolator.LINEAR);
			rotateTransition.play();
			this.grid[x][y].setGraphic(g);
		} else if (grid[x][y].getOrbs() == 3) {
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
			pm.setDiffuseColor(Color.RED);
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
		} else {
			this.grid[x][y].setGraphic(null);
			grid[x][y].setOrbs(0);
		}

	}
}
