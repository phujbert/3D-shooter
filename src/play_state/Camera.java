package play_state;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Collections;

import game_files.Game;

public class Camera {

	//a j�t�kos akin a kamera van
	private Player player;
	
	private Grid grid;
	private Handler handler;
	//camera plane
	private double planeX, planeY;
	//kirajzoland� pixeloszlopok
	private ArrayList<PixelCol> pixelCols;
	//l�t�t�vols�g
	public static int viewDistance = 10;
	
	/**
	 * A camera konstruktora
	 * @param grid A grid, amin j�tsz�dik a j�t�k
	 * @param handler A GameObjecteket kezeli
	 */
	public Camera(Grid grid, Handler handler) {
		this.grid = grid;
		this.handler = handler;
		
		planeX = 0.66;
		planeY = 0;
		
		
		pixelCols = new ArrayList<PixelCol>();
	}

	public void setPlayer(Player player) { this.player = player; }
	public Player getPlayer() { return this.player; }
	public PixelCol getPixelCol(int i) { return this.pixelCols.get(i); }
	
	
	/**
	 * A camera friss�t�s��rt felel�s
	 * A j�t�kossal egy�tt forgatja el azt
	 */
	public void tick() {
		double oldPlaneX = planeX;
		planeX = planeX * Math.cos(player.getRotSpeed()) - planeY * Math.sin(player.getRotSpeed());
		planeY = oldPlaneX * Math.sin(player.getRotSpeed()) + planeY * Math.cos(player.getRotSpeed());
	}

	/**
	 * A kamera l�t�ter�ben l�v� GameObjectek kirajzol�s��rt felel�s
	 * @param g A Jpanelben haszn�lt Graphics oszt�ly
	 */
	public void render(Graphics g) {
		//calculate walls
		pixelCols.clear();
		for (int i = 0; i < Game.WIDTH; i++) this.raycast(i);
		
		
		//calculate objects
		if (handler.object.size() > 0) Collections.sort(handler.object);
		for (int i = 0; i < handler.object.size(); i++) this.drawObject(handler.object.get(i));
		
		
		//render walls
		for (int i = 0; i < pixelCols.size(); i++) {
			PixelCol p = pixelCols.get(i);
			p.render(g, i);
		}
		
		
		//render objects
		for (int j = 0; j < handler.object.size(); j++) {
			GameObject to = handler.object.get(j);
			if (to != player) {
				to.render(g);
			}

		}
	}
	
	
	/**
	 * A raycasting-�rt felel�s f�ggv�ny.
	 * A param�terben megadott oszlop alapj�n hozz�ad egy pixeloszlopot azok t�mbj�hez
	 * (param�tereit �gy adja meg a pixeloszlopnak, hogy a sug�r el talt e falat vagy sem)
	 * A sug�r addig megy, ameddig nem tal�l el egy falat, vagy am�g ki nem �r a kamera l�t�t�vols�g�b�l
	 * @param i A k�perny� i. pixeloszlopa
	 */
	public void raycast(int i) {
		
			
		double cameraX = Game.map(i, 0, Game.WIDTH, -1, 1);
			
		double rayDirX = player.getDirX() + (this.planeX * cameraX);
		double rayDirY = player.getDirY() + (this.planeY * cameraX);
			
		int mapX = (int)Math.floor(player.x);
		int mapY = (int)Math.floor(player.y);
			
		double sideDistX, sideDistY;
			
		double deltaDistX = Math.abs(1 / rayDirX);
		double deltaDistY = Math.abs(1 / rayDirY);
		double perpWallDist;
			
		int stepX, stepY;
			
		int hit = 0;
		int side = 0;
			
		if (rayDirX < 0) {
			stepX = -1;
			sideDistX = (player.x - mapX) * deltaDistX;
		} else {
			stepX = 1;
			sideDistX = ((mapX + 1) - player.x) * deltaDistX;
		}
			
		if (rayDirY < 0) {
			stepY = -1;
			sideDistY = (player.y - mapY) * deltaDistY;
		} else {
			stepY = 1;
			sideDistY = ((mapY + 1) - player.y) * deltaDistY;
		}
			
		while (hit == 0 && (sideDistX < viewDistance || sideDistY < viewDistance)) {
			if (sideDistX < sideDistY) {
					
				sideDistX += deltaDistX;
				mapX += stepX;
					
				side = 0;
			} else {
				sideDistY += deltaDistY;
				mapY += stepY;
				side = 1;
			}
				
			if (grid.getCell(mapY, mapX) > 0) hit = grid.getCell(mapY, mapX);
		}
			
		if (side == 0) perpWallDist = (mapX - player.x + ((1 - stepX) / 2)) / rayDirX;
		else perpWallDist = (mapY - player.y + ((1 - stepY) / 2)) / rayDirY;
			
		pixelCols.add(new PixelCol(perpWallDist, hit, side, this.grid));
			
			
	}
	
	
	/**
	 * Kisz�molja egy megadott GameObject-nek a kamer�t�l vett relat�v t�vols�g�t (transformX)
	 * �s a kamera plane-ben val� elhelyezked�s�t (transformY)
	 * CSak akkor v�gzi el a sz�m�t�st, ha denom nem egyenl� null�val
	 * @param to A megadott GameObject
	 */
	public void drawObject(GameObject to) {
		
		if (to != player) {
			double denom = this.planeX * player.getDirY() - player.getDirX() * this.planeY;
			if (denom != 0) {
				double tempX = to.getX() - player.getX();
				double tempY = to.getY() - player.getY();
					
				double invDet = 1.0 / (denom);
					
				double transformX = invDet * (player.getDirY() * tempX - player.getDirX() * tempY);
				double transformY = invDet * (-this.planeY * tempX + this.planeX * tempY);
					
				to.setTransformX(transformX);
				to.setTransformY(transformY);
			}
			
				
				
		}
		
	}
	
	
	
}
