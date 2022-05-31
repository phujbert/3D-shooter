package play_state;

import java.awt.Color;
import java.awt.Graphics;

import game_files.Game;

public class PixelCol {

	//A fal oszlopának távolsága a kamerától
	private double perpWallDist;
	//A fal erõssége
	private int hit;
	//Megmutatja, hogy ez az oszlop melyik oldala a falnak
	private int side;
	private Grid grid;
	
	/**
	 * Az osztály konstruktora
	 * @param perpWallDist A játékosnak a faltól vett relatív távolsága
	 * @param hit A fal értéke
	 * @param side A fal oldala
	 * @param grid A grid amin zajlik a játék
	 */
	public PixelCol(double perpWallDist, int hit, int side, Grid grid) {
		this.perpWallDist = perpWallDist;
		this.hit = hit;
		this.side = side;
		this.grid = grid;
		
	}
	
	public int getHit() { return this.hit; }
	public double getPerpWallDist() { return this.perpWallDist; }
	
	
	/**
	 * Kirajzolja a pixeloszlopot
	 * @param g A hasznát Graphics osztály
	 * @param x A pixeloszlop x koordinátija a képernyõn
	 */
	public void render(Graphics g, int x) {
		int drawStart, drawEnd;
		
		double lineHeight = (Game.HEIGHT / perpWallDist);
		
		drawStart = (int)((-lineHeight / 2) + (Game.HEIGHT / 2));
		if (drawStart < 0) drawStart = 0;
		drawEnd = (int)((lineHeight / 2) + (Game.HEIGHT / 2));
		if (drawStart >= Game.HEIGHT) drawEnd = Game.HEIGHT - 1;
		
		Color color;
		
		if (hit > 0) {
			double alpha = Game.map(perpWallDist, 0, Camera.viewDistance, 255, 0);
			if (side == 0) {
				double red = Game.map(hit, 0, grid.getWallStrength(), 50, 148);
				color = new Color((int)red, 46, 46, (int)alpha);
			} else {
				double red = Game.map(hit, 0, grid.getWallStrength(), 50, 94);
				color = new Color((int)red, 34, 34, (int)alpha);
			}
			
		} else {
			color = Color.black;
		}
		g.setColor(color);
		g.fillRect(x, drawStart, 1, drawEnd - drawStart);
	}
	
	
}
