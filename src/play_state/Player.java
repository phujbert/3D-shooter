package play_state;

import game_files.Game;

public class Player extends Entity {
	
	private double absoluteSpeed = 0.07;
	
	/**
	 * AZ osztály konstruktora
	 * @param x A player x koordinátája
	 * @param y A player y koordinátája
	 * @param grid A grid amin zajlik a játék
	 * @param camera A kamera, amin keresztül a játékos látja a játékot
	 * @param handler A GameObject-ek kezelõje
	 */
	public Player(double x, double y, Grid grid, Camera camera, Handler handler) {
		super(x, y, grid, camera, handler);
		this.id = ID.Player;
		this.health = 100;
		this.radius = 0.3;
		this.gun = new Gun(this, 20);
	}
	
	/**
	 * Frissíti a player állapotát
	 */
	public void tick() {
		
		this.rotate();
		this.move();
	
		this.gun.tick();
		
		this.collision();
	}
	
	/**
	 * Változtatja az x, y koordinátát az irány és a sebesség alapján
	 */
	public void move() {
		//az elmozdulas erteke
		double moveX = (dirX * Math.cos(sideMove) - dirY * Math.sin(sideMove)) * moveSpeed;
		double moveY = (dirX * Math.sin(sideMove) + dirY * Math.cos(sideMove)) * moveSpeed;
				
				
		//mozgatas
		double newX = x + moveX;
		double newY = y + moveY;		
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
	 * Elforgatja a player irányvektorát a forgási sebesség alapján
	 */
	public void rotate() {
		//forgatas
		double oldDirX = dirX;
		dirX = dirX * Math.cos(rotSpeed) - dirY * Math.sin(rotSpeed);
		dirY = oldDirX * Math.sin(rotSpeed) + dirY * Math.cos(rotSpeed);
	}
	
	/**
	 * Ellenörzi, hogy ütközik-e a player egy másik GameObjecttel
	 * Ha az egy EnemyBullet akkor csökkenti a player életét
	 */
	protected void collision() {
		for (int i = 0; i < handler.object.size(); i++) {
			GameObject tempObject = handler.object.get(i);
			
			if (this.getDist(tempObject) < (this.radius + tempObject.getRadius())) {
				if (tempObject.getID() == ID.EnemyBullet) {
					this.health -= 5;
					tempObject.setHealth(0);
				}

			}
				
				
		}
		this.health = Game.clamp(this.health, 0, 100);

	}
	
	public double getAbsoluteSpeed() { return this.absoluteSpeed; }
	public void setAbsolutSpeed(double as) { this.absoluteSpeed = as; }

	

}
