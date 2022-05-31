package play_state;

import java.awt.Color;
import java.awt.Graphics;

import game_files.Game;

public class PixelCol {

	//A fal oszlop�nak t�vols�ga a kamer�t�l
	private double perpWallDist;
	//A fal er�ss�ge
	private int hit;
	//Megmutatja, hogy ez az oszlop melyik oldala a falnak
	private int side;
	private Grid grid;
	
	/**
	 * Az oszt�ly konstruktora
	 * @param perpWallDist A j�t�kosnak a falt�l vett relat�v t�vols�ga
	 * @param hit A fal �rt�ke
	 * @param side A fal oldala
	 * @param grid A grid amin zajlik a j�t�k
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
	 * @param g A haszn�t Graphics oszt�ly
	 * @param x A pixeloszlop x koordin�tija a k�perny�n
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
