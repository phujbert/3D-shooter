package play_state;

import java.awt.Graphics;

import game_files.Game;

public abstract class Entity extends GameObject{

	//az entit�s fegyvere
	protected Gun gun;
	
	/**
	 * A Gun oszt�ly konstruktora
	 * @param x A gun x koordin�t�ja
	 * @param y A gun y koordin�t�ja
	 * @param grid A grid amin zajlik a j�t�k
	 * @param camera A kamera, amin kereszt�l a j�t�kos l�tja a j�t�kot
	 * @param handler A GameObject-ek kezel�je
	 */
	public Entity(double x, double y, Grid grid, Camera camera, Handler handler) {
		super(x, y, grid, camera, handler);
	}

	public abstract void tick();
	protected abstract void collision();
	public abstract void move();
	public abstract void rotate();

	/**
	 * Az entity kirajzol�s��rt felel�s,
	 * Ha az a kamera l�t�ter�ben van
	 */
	public void render(Graphics g) {
		double h = (Game.HEIGHT / this.transformY) / 1.7;
		double w = (h / img.getHeight()) * img.getWidth();
		
		int screenX = (int)((Game.WIDTH / 2) * (1 + transformX / transformY) - (w / 2));
		
		for (int i = 0; i < img.getWidth(); i++) {
			int dx1 = (int)(screenX + i * w / img.getWidth());
			int dy1 = (int)((Game.HEIGHT / 2) + ((Game.HEIGHT / this.transformY) / 2) - h);
			int dx2 = (int)(screenX + (i + 1) * w / img.getWidth());
			int dy2 = (int)((Game.HEIGHT / 2) + ((Game.HEIGHT / this.transformY) / 2));
			if (transformY > 0 && transformY < Camera.viewDistance && dx1 > 0 && dx1 < Game.WIDTH && transformY < camera.getPixelCol(dx1).getPerpWallDist())
				g.drawImage(img, dx1, dy1, dx2, dy2, i, 0, i + 1, img.getHeight(), null);
		}
	}
	
	public void setGun(Gun gun) { this.gun = gun; }
	public Gun getGun() { return this.gun; }
}
