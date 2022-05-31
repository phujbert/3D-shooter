package play_state;

import java.util.Random;

public class Grid {
	
	//Az oszlopok �s sorok sz�ma
	private int rows, cols;
	//2D array a mapnek
	private int[][] map;
	//Fal er�ss�ge
	private int wallStrength = 1000;
	private Random rand;
	
	/**
	 * Az oszt�ly konstruktora
	 * L�trehoz egy n darab m hossz�s�g� t�mb�t tartalmaz� t�mb�t
	 * �s felt�lti az 0 - wallStrength terjed� �rt�kkel
	 * @param n A sorok sz�ma
	 * @param m Az oszlopok sz�ma
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
	 * Visszaadja a cella �rt�k�t a megadott x, y helyen
	 * A grid itt kezeli le, hogy a grid az x tengely ment�n megfordulhat
	 * @param y Az y koordin�ta
	 * @param x Az x koordin�ta
	 * @return Az x, y helyen l�v� cella �rt�ke
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
	 * A megadott helyen l�v� cella �rt�k�t cs�kkenti eggyel
	 * @param y Az y koordin�ta
	 * @param x Az x koordin�ta
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
