package play_state;

import java.util.Random;

public class Gun {

	//Az entit�s akihez tartozik a fegyver
	Entity entity;
	//Seg�dv�ltoz� az eltelt 'id�' sz�mol�s�hoz
	private int counter;
	//A fegyver �jrat�lt�s�nek ideje
	private int reload;
	//V�ltoz�, hogy fel van e t�ltve a fegyver
	private boolean loaded;
	private Random r;
	//Id arra, hogy milyem entit�s l�tt a fegyverrel egy goly�t
	private ID bulletID = ID.Unknown;
	
	
	/**
	 * Az oszt�ly konstruktora
	 * @param entity Az entity aki� a fegyver
	 * @param reload A reload time ideje
	 */
	public Gun(Entity entity, int reload) {
		this.entity = entity;
		this.reload = reload;
		r = new Random();
		this.counter = r.nextInt(reload);
		if (this.entity.getID() == ID.Player) this.bulletID = ID.PlayerBullet;
		else if (this.entity.getID() == ID.Enemy) this.bulletID = ID.EnemyBullet;
	}
	
	/**
	 * Friss�ti a fegyvert �llapot�t
	 */
	public void tick() {
		if (this.counter >= this.reload) {
			loaded = true;
		} else counter++;
		
	}
	
	
	/**
	 * L�trehoz egy �j bulletet, ami a z entity ir�ny�ba mutat
	 */
	public void shoot() {
		Bullet b = new Bullet(entity.getX(), entity.getY(), bulletID, entity.getGrid(), entity.getCamera(), entity.getHandler());
		b.setDirX(entity.getDirX());
		b.setDirY(entity.getDirY());
		entity.getHandler().addObject(b);
		this.loaded = false;
		this.counter = 0;
		
	}
	
	public void setReload(int reload) { this.reload = reload; }
	public int getReload() { return this.reload; }
	public int getCounter() { return this.counter; }
	public boolean getLoaded() { return this.loaded; }
}
