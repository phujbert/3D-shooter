package play_state;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public abstract class GameObject implements Comparable<GameObject>{

	//Az x, y koordin�t
	protected double x, y;
	
	protected ID id;
	protected Grid grid;
	protected Camera camera;
	//Az objectum ir�nyvektora
	protected double dirX, dirY;
	//A forg�si sebess�g
	protected double rotSpeed;
	//A mozg�si sebess�g
	protected double moveSpeed;
	//Elfordul�s �rtke (a jobbra balra bozg�shoz kell)
	protected double sideMove;
	//A kamera planeben val� elhelyezked�s koordin�t�i (transformY a kamer�t�l val� relat�v t�vols�g)
	protected double transformX, transformY;
	//Az objektum megjelen�t�s�hez haszn�lt k�p
	protected BufferedImage img;
	//�leter�
	protected int health;
	protected Handler handler;
	//Az objektum kiterjed�se
	protected double radius;

	
	/**
	 * A GameObject oszt�ly konstruktora
	 * @param x Az objektum x koordin�t�ja
	 * @param y Az objektum y koordin�t�ja
	 * @param grid A grid amin zajlik a j�t�k
	 * @param camera A kamera, amin kereszt�l a j�t�kos l�tja a j�t�kot
	 * @param handler A GameObject-ek kezel�je
	 */
	public GameObject(double x, double y, Grid grid, Camera camera, Handler handler) {
		this.x = x;
		this.y = y;
		this.grid = grid;
		this.camera = camera;
		this.handler = handler;
		this.dirX = 0;
		this.dirY = -1;
	}
	
	/**
	 * �sszehasonl�tja az objektumot egy m�sik GameObject-el a Player-t�l vett t�vols�guk alapj�n
	 */
	public int compareTo(GameObject c) {
		GameObject comp = (GameObject)c;
		double camX = this.camera.getPlayer().getX();
		double camY = this.camera.getPlayer().getY();
		double compDist = Math.pow((camX - comp.getX()), 2) + Math.pow((camY - comp.getY()), 2);
		double thisDist = Math.pow((camX - this.x), 2) + Math.pow((camY - this.y), 2);
		if (thisDist < compDist) return 1;
		else if (thisDist > compDist) return -1;
		else return 0;
	}
	
	
	public abstract void tick();
	public abstract void render(Graphics g);
	public abstract void move();
	
	
	/**
	 * Megadja egy GameObject t�vols�g�t az objektumt�l
	 * @param o A megadott GameObject
	 * @return A kett� t�vols�ga
	 */
	public double getDist(GameObject o) {
		return Math.sqrt(Math.pow((this.x - o.getX()), 2) + Math.pow((this.y - o.getY()), 2));
	}
	
	
	public void setX(double x) { this.x = x; }
	public void setY(double y) { this.y = y; }
	public double getX() { return this.x; }
	public double getY() { return this.y; }
	public void setDirX(double dx) { this.dirX = dx; }
	public void setDirY(double dy) { this.dirY = dy; }
	public double getDirX() { return this.dirX; }
	public double getDirY() { return this.dirY; }
	public void setMoveSpeed(double s) { this.moveSpeed = s; }
	public double getMoveSpeed() { return this.moveSpeed; }
	public void setRotSpeed(double s) { this.rotSpeed = s; }
	public double getRotSpeed() { return this.rotSpeed; }
	public void setSideMove(double sm) { this.sideMove = sm; }
	public double getSideMove() { return this.sideMove; }
	public Camera getCamera() { return this.camera; }
	public void setCamera(Camera camera) { this.camera = camera; }
	public double getTransformX () { return this.transformX; }
	public void setTransformX(double transformX) { this.transformX = transformX; }
	public double getTransformY() { return this.transformY; }
	public void setTransformY(double transformY) { this.transformY = transformY; }
	public int getHealth() { return this.health; }
	public void setHealth(int health) { this.health = health; }
	public ID getID() { return this.id; }
	public double getRadius() { return this.radius; }
	public Grid getGrid() { return this.grid; }
	public Handler getHandler() { return this.handler; }
	
}
