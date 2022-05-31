package play_state;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Enemy extends Entity {
	
	//a j�t�kos akin a kamera van
	private Player player;

	
	/**
	 * Az Enemy oszt�ly konstruktora
	 * @param x A bullet x koordin�t�ja
	 * @param y A bullet y koordin�t�ja
	 * @param grid A grid amin zajlik a j�t�k
	 * @param camera A kamera, amin kereszt�l a j�t�kos l�tja a j�t�kot
	 * @param handler A GameObject-ek kezel�je
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
	 * Az enemy friss�t�s��rt felel�s
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
	 * Az enemy koordin�t�it v�ltoztatja az ir�nya �s sebess�ge alapj�n, ha nem �tk�zik falba
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
	 * Az enemy ir�ny�t v�ltoztatja, �gy hogy az mindig a player ir�ny�ba mutasson
	 */
	public void rotate() {
		this.dirX = (player.getX() - this.x) / transformY;
		this.dirY = (player.getY() - this.y) / transformY;
	}
	
	
	/**
	 * Ellen�rzi, hogy az enemy �ssze�tk�z�tt-e egy m�sik GameObjecttel
	 * Ha igen akkor a GameObject id-ja alapj�n cs�kkenti az enemy �let�t
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
