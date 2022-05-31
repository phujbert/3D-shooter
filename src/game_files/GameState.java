package game_files;

import java.awt.Graphics;

public abstract class GameState {

	//A GameStateManager
	protected GameStateManager gsm;
	
	public GameState(GameStateManager gsm) {
		this.gsm = gsm;
		this.init();
	}
	
	public abstract void init();
	public abstract void tick();
	public abstract void render(Graphics g);
	public abstract void keyPressed(int key);
	public abstract void keyReleased(int key);
}
