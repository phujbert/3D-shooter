package play_state;

import java.util.Random;

public class Grid {
	
	//Az oszlopok és sorok száma
	private int rows, cols;
	//2D array a mapnek
	private int[][] map;
	//Fal erõssége
	private int wallStrength = 1000;
	private Random rand;
	
	/**
	 * Az osztály konstruktora
	 * Létrehoz egy n darab m hosszúságú tömböt tartalmazó tömböt
	 * És feltölti az 0 - wallStrength terjedõ értékkel
	 * @param n A sorok száma
	 * @param m Az oszlopok száma
	 */
	public Grid(int n, int m) {
		this.rand = new Random();
		this.rows = n;
		this.cols = m;
		this.map = new int[this.rows][this.cols];
		for (int i = 0; i < this.rows; i++) {
			for (int j = 0; j < this.cols; j++) {
				if (Math.abs(rand.nextInt() % 8) == 1) this.map[i][j] = this.wallStrength;
				else this.map[i][j] = 0;
			}
		}
		
	}
	
	public int getRows() { return this.rows; }
	public int getCols() { return this.cols; }
	public void setCell(int row, int col, int val) { this.map[row][col] = val; }
	
	/**
	 * Visszaadja a cella értékét a megadott x, y helyen
	 * A grid itt kezeli le, hogy a grid az x tengely mentén megfordulhat
	 * @param y Az y koordináta
	 * @param x Az x koordináta
	 * @return Az x, y helyen lévõ cella értéke
	 */
	public int getCell(double y, double x) {
		int hit;
		int col = (int)(((x % this.cols) + this.cols) % this.cols);
		int row = (int)(((y % this.rows) + this.rows) % this.rows);
		if ((int)((((x / this.cols) % 2) + 2) % 2) == 1) {
			row = (this.rows - 1) - row;
			hit = map[row][col];
		} else hit = map[row][col];
		return hit;
	}
	
	/**
	 * A megadott helyen lévõ cella értékét csökkenti eggyel
	 * @param y Az y koordináta
	 * @param x Az x koordináta
	 */
	public void destroyWall(double y, double x) {
		int col = (int)(((x % this.cols) + this.cols) % this.cols);
		int row = (int)(((y % this.rows) + this.rows) % this.rows);
		if ((int)((((x / this.cols) % 2) + 2) % 2) == 1) {
			row = (this.rows - 1) - row;
			map[row][col]--;
		} else map[row][col]--;
	}
	
	
	public int getWallStrength() { return this.wallStrength; }
		
	
	
}
