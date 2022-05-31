package play_state;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Enemy extends Entity {
	
	//a játékos akin a kamera van
	private Player player;

	
	/**
	 * Az Enemy osztály konstruktora
	 * @param x A bullet x koordinátája
	 * @param y A bullet y koordinátája
	 * @param grid A grid amin zajlik a játék
	 * @param camera A kamera, amin keresztül a játékos látja a játékot
	 * @param handler A GameObject-ek kezelõje
	 */
	public Enemy(double x, double y, Grid grid, Camera camera, Handler handler) {
		super(x, y, grid, camera, handler);
		this.id = ID.Enemy;
		this.player = camera.getPlayer();
		try {
		    this.img = ImageIO.read(new File("doom_zombie.png"));
		} catch (IOException e) {
			System.out.println("Nem sikerult beolvasni a kepet");
		}
		
		this.health = 1;
		this.radius = 0.15;
		this.moveSpeed = 0.01;
		this.gun = new Gun(this, 100);
	}
	
	
	/**
	 * Az enemy frissítéséért felelõs
	 */
	public void tick() {
			
		if (transformY != 0) {
			this.rotate();
			this.move();
			
		}
		

		this.gun.tick();
		if (this.gun.getLoaded()) gun.shoot();
		
		this.collision();
	}
	
	
	/**
	 * Az enemy koordinátáit változtatja az iránya és sebessége alapján, ha nem ütközik falba
	 */
	public void move() {
		double newX = x + (dirX * moveSpeed);
		double newY = y + (dirY * moveSpeed);
		if (grid.getCell(newY + this.radius, x  + this.radius) == 0
				&& grid.getCell(newY + this.radius, x  - this.radius) == 0
				&& grid.getCell(newY - this.radius, x  + this.radius) == 0
				&& grid.getCell(newY - this.radius, x  - this.radius) == 0) y = newY;
		if (grid.getCell(y + this.radius, newX + this.radius) == 0
				&& grid.getCell(y + this.radius, newX - this.radius) == 0
				&& grid.getCell(y - this.radius, newX + this.radius) == 0
				&& grid.getCell(y - this.radius, newX - this.radius) == 0) x = newX;
	}
	
	/**
	 * Az enemy irányát változtatja, úgy hogy az mindig a player irányába mutasson
	 */
	public void rotate() {
		this.dirX = (player.getX() - this.x) / transformY;
		this.dirY = (player.getY() - this.y) / transformY;
	}
	
	
	/**
	 * Ellenõrzi, hogy az enemy összeütközött-e egy másik GameObjecttel
	 * Ha igen akkor a GameObject id-ja alapján csökkenti az enemy életét
	 */
	protected void collision() {
		for (int i = 0; i < handler.object.size(); i++) {
			GameObject tempObject = handler.object.get(i);
			
			if (this.getDist(tempObject) < (this.radius + tempObject.getRadius())) {
				if (tempObject.getID() == ID.PlayerBullet) {
					this.health--;
					tempObject.setHealth(0);
				}

			}
		}
	}
	
	

	

}
