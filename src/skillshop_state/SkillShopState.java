package skillshop_state;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

import game_files.Game;
import game_files.GameState;
import game_files.GameStateManager;
import play_state.Level;
import play_state.Player;

public class SkillShopState extends GameState {
	
	private Player player;
	private Level level;
	// Jelenleg melyik upgrade lehetõség van kiválasztva
	private int current = 0;
	//Az upgrade lehetõségek száma
	private int all = 3;
	//Ennyibe kerül az életerõ fejlesztése
	private int healtCost = 10;
	//Ennyibe kerül a fegyver fejlesztése
	private int gunCost = 50;
	//Ennyibe kerül a mozgás fejlesztése
	private int moveCost = 50;

	public SkillShopState(GameStateManager gsm, Player player, Level level) {
		super(gsm);
		this.player = player;
		this.level = level;
	}

	public void init() {}
	public void tick() {}

	/**
	 * Kirajzolja a SkillShopState alatt látható dolgokat
	 */
	public void render(Graphics g) {
		g.setColor(Color.white);
		g.setFont(new Font("arial", 0, 40));
		
		
		g.setColor(Color.white);
		g.drawString("Cash : " + level.getCash(), Game.WIDTH / 4 - 150, Game.HEIGHT / 4 - 75);
		
		//0. upgrade
		if (current == 0) {
			g.setColor(Color.gray);
			g.fillRect(Game.WIDTH / 4 - 150, Game.HEIGHT / 4 - 50, 300, 100);
		}
		g.setColor(Color.white);
		g.drawRect(Game.WIDTH / 4 - 150, Game.HEIGHT / 4 - 50, 300, 100);
		g.drawString("+10 Health point", Game.WIDTH / 4 - 145, Game.HEIGHT / 4 + 10);
		
		//1. upgrade
		if (current == 1) {
			g.setColor(Color.gray);
			g.fillRect(Game.WIDTH / 4 - 150, 2 * Game.HEIGHT / 4 - 50, 300, 100);
		}
		g.setColor(Color.white);
		g.drawRect(Game.WIDTH / 4 - 150, 2 * Game.HEIGHT / 4 - 50, 300, 100);
		g.drawString("Upgrade weapon", Game.WIDTH / 4 - 150, 2 * Game.HEIGHT / 4 + 10);

		
		//2. upgrade
		if (current == 2) {
			g.setColor(Color.gray);
			g.fillRect(Game.WIDTH / 4 - 150, 3 * Game.HEIGHT / 4 - 50, 300, 100);
		}
		g.setColor(Color.white);
		g.drawRect(Game.WIDTH / 4 - 150, 3 * Game.HEIGHT / 4 - 50, 300, 100);
		g.drawString("Upgrade speed", Game.WIDTH / 4 - 135, 3 * Game.HEIGHT / 4 + 10);

		
		//draw the costs
		g.setFont(new Font("arial", 0, 20));
		g.drawString("Costs: " + this.healtCost, Game.WIDTH / 4 - 145, Game.HEIGHT / 4 + 40);
		g.drawString("Costs: " + this.gunCost, Game.WIDTH / 4 - 150, 2 * Game.HEIGHT / 4 + 40);
		g.drawString("Costs: " + this.moveCost, Game.WIDTH / 4 - 135, 3 * Game.HEIGHT / 4 + 40);

	}

	/**
	 * Lekezeli a SkillShpState alatti key inputot
	 */
	public void keyPressed(int key) {
		
		//back to play state
		if (key == KeyEvent.VK_E) gsm.states.pop();
		
		//move in the shop
		if (key == KeyEvent.VK_UP) current = Game.clamp(current - 1, 0, all - 1);
		if (key == KeyEvent.VK_DOWN) current = Game.clamp(current + 1, 0, all - 1);
		
		//buy selected item
		if (key == KeyEvent.VK_ENTER) {
			if (current == 0) {
				if (player.getHealth() < 100 && level.getCash() >= this.healtCost) {
					int newHealth = Game.clamp(player.getHealth() + 10, 0, 100);
					player.setHealth(newHealth);
					int newCash = Game.min(level.getCash() - this.healtCost, 0);
					level.setCash(newCash);
				}
				
			} else if (current == 1) {
				if (player.getGun().getReload() > 5 && level.getCash() >= this.gunCost) {
					int reloadTime = Game.min(player.getGun().getReload() - 1, 5);
					player.getGun().setReload(reloadTime);
					int newCash = Game.min(level.getCash() - this.gunCost, 0);
					level.setCash(newCash);
				}
				
			} else if (current == 2) {
				if (player.getAbsoluteSpeed() < 1.5 && level.getCash() >= this.moveCost) {
					double as = player.getAbsoluteSpeed() + 0.01;
					if (as > 0.15) as = 0.15;
					player.setAbsolutSpeed(as);
					int newCash = Game.min(level.getCash() - this.moveCost, 0);
					level.setCash(newCash);
				}
				
			}
		}
	}

	public void keyReleased(int key) {}

}
