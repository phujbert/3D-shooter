package play_state;

import java.util.Random;

public class Gun {

	//Az entitás akihez tartozik a fegyver
	Entity entity;
	//Segédváltozó az eltelt 'idõ' számolásához
	private int counter;
	//A fegyver újratöltésének ideje
	private int reload;
	//Változó, hogy fel van e töltve a fegyver
	private boolean loaded;
	private Random r;
	//Id arra, hogy milyem entitás lõtt a fegyverrel egy golyót
	private ID bulletID = ID.Unknown;
	
	
	/**
	 * Az osztály konstruktora
	 * @param entity Az entity akié a fegyver
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
	 * Frissíti a fegyvert állapotát
	 */
	public void tick() {
		if (this.counter >= this.reload) {
			loaded = true;
		} else counter++;
		
	}
	
	
	/**
	 * Létrehoz egy új bulletet, ami a z entity irányába mutat
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
