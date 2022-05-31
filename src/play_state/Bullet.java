package play_state;

import game_files.*;
import java.awt.Graphics;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Bullet extends GameObject{
	
	
	/**
	 * A Bullet osztály konstruktora
	 * @param x A bullet x koordinátája
	 * @param y A bullet y koordinátája
	 * @param id A bullet ID-ja
	 * @param grid A grid amin zajlik a játék
	 * @param camera A kamera, amin keresztül a játékos látja a játékot
	 * @param handler A GameObject-ek kezelõje
	 */
	public Bullet(double x, double y, ID id, Grid grid, Camera camera, Handler handler) {
		super(x, y, grid, camera, handler);
		this.id = id;
		this.health = 100;
		this.moveSpeed = 0.1;
		this.radius = 0.05;
		
		try {
		    this.img = ImageIO.read(new File("bullet.png"));
		} catch (IOException e) {
			System.out.println("Nem sikerult beolvasni a kepet");
		}
	}

	/**
	 * A bullet frissítéséért felelõs, mozgatja azt és csökkenti az életét ha szükséges
	 */
	public void tick() {
		
		this.move();
		
		//decrement bullets health if it's to far
		if (this.getDist(this.camera.getPlayer()) > Camera.viewDistance) health--;
		
	}
	
	
	/**
	 * A bullet iránya és sebessége alapján változtatja annak helyét.
	 * Ellenõrzi, ha eltalát falat akkor a bullet életét nullára,
	 * a fal erõsségét pedig egyel csökkenti
	 */
	public void move() {
		//move bullet
		double newX = x + (dirX * moveSpeed);
		double newY = y + (dirY * moveSpeed);
		
		//check if bullet hits wall, if yes then destroy it
		if (grid.getCell(newY + this.radius, x  + this.radius) != 0) {
			health = 0;
			grid.destroyWall(newY + this.radius, x  + this.radius);
		} else if (grid.getCell(newY + this.radius, x  - this.radius) != 0) {
			health = 0;
			grid.destroyWall(newY + this.radius, x  - this.radius);

		} else if (grid.getCell(newY - this.radius, x  + this.radius) != 0) {
			health = 0;
			grid.destroyWall(newY - this.radius, x  + this.radius);

		} else if (grid.getCell(newY - this.radius, x  - this.radius) != 0) {
			health = 0;
			grid.destroyWall(newY - this.radius, x  - this.radius);

		} else y = newY;
				
		if (grid.getCell(y + this.radius, newX + this.radius) != 0) {
			health = 0;
			grid.destroyWall(y + this.radius, newX + this.radius);

		} else if (grid.getCell(y + this.radius, newX - this.radius) != 0) {
			health = 0;
			grid.destroyWall(y + this.radius, newX - this.radius);

		} else if (grid.getCell(y - this.radius, newX + this.radius) != 0) {
			health = 0;
			grid.destroyWall(y - this.radius, newX + this.radius);

		} else if (grid.getCell(y - this.radius, newX - this.radius) != 0) {
			health = 0;
			grid.destroyWall(y - this.radius, newX - this.radius);

		} else x = newX;
	}
	
	
	/**
	 * A bullet-et rajzolja ki a képernyõre, ha az a camera látóterében van
	 */
	public void render(Graphics g) {
		double h = (Game.HEIGHT / this.transformY) / 10;
		double w = (h / img.getHeight()) * img.getWidth();
		
		int screenX = (int)((Game.WIDTH / 2) * (1 + transformX / transformY) - (w / 2));
		
		for (int i = 0; i < img.getWidth(); i++) {
			int dx1 = (int)(screenX + i * w / img.getWidth());
			int dy1 = (int)((Game.HEIGHT / 2) + ((Game.HEIGHT / this.transformY) / 4) - h);
			int dx2 = (int)(screenX + (i + 1) * w / img.getWidth());
			int dy2 = (int)((Game.HEIGHT / 2) + ((Game.HEIGHT / this.transformY) / 4));
			if (transformY > 0 && transformY < Camera.viewDistance && dx1 > 0 && dx1 < Game.WIDTH && transformY < camera.getPixelCol(dx1).getPerpWallDist())
				g.drawImage(img, dx1, dy1, dx2, dy2, i, 0, i + 1, img.getHeight(), null);
		}
		
	}

}
