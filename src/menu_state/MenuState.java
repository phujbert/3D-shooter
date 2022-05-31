package menu_state;

import game_files.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

import leaderboard_state.LeaderBoardState;
import play_state.PlayState;

public class MenuState extends GameState{
	
	//A jelenleg kiválasztott menüpont
	private int current = 0;
	//A menüpontok száma
	private int all = 3;
	
	public MenuState(GameStateManager gsm) {
		super(gsm);
	}
	
	public void init() {}
	public void tick() {}
	
	
	/**
	 * A MenuState key inputot kezelõ függvénye
	 */
	public void keyPressed(int key) {
		
			if (key == KeyEvent.VK_UP) current = Game.clamp(current - 1, 0, all - 1);
			if (key == KeyEvent.VK_DOWN) current = Game.clamp(current + 1, 0, all - 1);
			if (key == KeyEvent.VK_ENTER) {
				if (current == 0) gsm.states.push(new PlayState(gsm));
				else if (current == 1) gsm.states.push(new LeaderBoardState(gsm));
				else if (current == 2) System.exit(1);
			}
		
	}
	
	
	/**
	 * Kirajzolja a menüt
	 */
	public void render(Graphics g) {		
		g.setColor(Color.white);
		g.setFont(new Font("arial", 0, 40));
		
		//start
		if (current == 0) {
			g.setColor(Color.gray);
			g.fillRect(Game.WIDTH / 4 - 150, Game.HEIGHT / 4 - 50, 300, 100);
		}
		g.setColor(Color.white);
		g.drawRect(Game.WIDTH / 4 - 150, Game.HEIGHT / 4 - 50, 300, 100);
		g.drawString("Start", Game.WIDTH / 4 - 50, Game.HEIGHT / 4 + 10);
		
		//leaderboard
		if (current == 1) {
			g.setColor(Color.gray);
			g.fillRect(Game.WIDTH / 4 - 150, 2 * Game.HEIGHT / 4 - 50, 300, 100);
		}
		g.setColor(Color.white);
		g.drawRect(Game.WIDTH / 4 - 150, 2 * Game.HEIGHT / 4 - 50, 300, 100);
		g.drawString("Leaderboard", Game.WIDTH / 4 - 110, 2 * Game.HEIGHT / 4 + 10);

		
		//quit
		if (current == 2) {
			g.setColor(Color.gray);
			g.fillRect(Game.WIDTH / 4 - 150, 3 * Game.HEIGHT / 4 - 50, 300, 100);
		}
		g.setColor(Color.white);
		g.drawRect(Game.WIDTH / 4 - 150, 3 * Game.HEIGHT / 4 - 50, 300, 100);
		g.drawString("Quit", Game.WIDTH / 4 - 40, 3 * Game.HEIGHT / 4 + 10);

	}

	public void keyReleased(int key) {}
	
}
