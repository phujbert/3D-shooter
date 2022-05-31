package play_state;

import game_files.*;
import java.util.Random;

import end_state.EndState;

public class Level {

	private Player player;
	private Handler handler;
	private Grid grid;
	private Camera camera;
	private Random random;
	private GameStateManager gsm;
	
	//A jelenlegi szintje a játéknak
	private int level = 1;
	//A játékos pénzze
	private int cash = 0;
	//A játékos pontja
	private int score = 0;
	//Számláló a megölt a szinten megölt enemy-knek
	private int enemiesKilled = 0;
	//Ennyi enemy-t kell megölni a szinten
	private int enemiesToKill = 10;
	
	
	/**
	 * Az osztály konstruktora
	 * Inicializálja az osztály változóit
	 * És hozzáad enemiesToKill számú enemy-t
	 * @param gsm A GameStateManager
	 * @param handler A GameObject-ek kezelõje
	 * @param grid A grid amin zajlik a játék
	 * @param camera A kamera, amin keresztül a játékos látja a játékot
	 */
	public Level(GameStateManager gsm, Handler handler, Grid grid, Camera camera) {
		this.gsm = gsm;
		this.player = camera.getPlayer();
		this.handler = handler;
		this.grid = grid;
		this.camera = camera;
		random = new Random();
		
		this.addEnemies(this.enemiesToKill);
		
	}
	
	/**
	 * Frissíti a level állapotát
	 * Ha egy GameObject élete 0 vagy az alá csökken akkor eltávolítja azt a handler object tömbjébõl
	 * Ha a Player élete csökken le 0 vagy az alá akkor kilép a PlayState-bõl
	 * Ha megöltünk megfelelõ mennyíségû enemy-t akkor növeli eggyel az osztály level változóját
	 */
	public void tick() {
		
		//check death
		if (player.getHealth() <= 0) {
			gsm.states.pop();
			gsm.states.push(new EndState(gsm, this.score));
		}
		
		//remove objects from level
		GameObject tempObject;
		for (int i = this.handler.object.size() - 1; i >= 0; i--) {
			tempObject = this.handler.object.get(i);
						
			if (tempObject.getHealth() <= 0) {
				if(tempObject.getID() == ID.Enemy) {
					this.cash += this.level * 10;
					this.score += this.level * 10;
					this.enemiesKilled++;
					this.addEnemies(1);
				}
				handler.removeObject(tempObject);
			}
		}
		
		if (this.enemiesKilled >= this.enemiesToKill) this.levelUp();
		
		
		
		
	}
	
	
	/**
	 * Létrehoz egy új enemy-t, inicializálja azt, és hozzáadja a játékhoz
	 * @param c
	 */
	public void addEnemies(int c) {
		for (int i = 0; i < c; i++ ) {
			int x = 0, y = 0;
			do {
				x = Game.clamp(random.nextInt() % 40, (int)player.getX() - 20, (int)player.getX() + 20);
				y = Game.clamp(random.nextInt() % 40, (int)player.getY() - 20, (int)player.getY() + 20);
			} while (grid.getCell(y, x) != 0);
			
			Enemy e = new Enemy((int)x + 0.5, (int)y + 0.5, this.grid, this.camera, this.handler);
			
			//set new reload time
			int reloadTime = Game.min(100 - (2 * this.level), 30);
			e.setGun(new Gun(e, reloadTime));
			
			//set new move speed
			double moveSpeed = 0.01 + (level * 0.005);
			if (moveSpeed > 0.05) moveSpeed = 0.05;
			e.setMoveSpeed(moveSpeed);
			
			//set new health
			e.setHealth(level);
			this.handler.addObject(e);

		}
	}
	
	
	/**
	 * Törli az összes enemy-t a handler object tömbjébõl
	 */
	public void clearEnemies() {
		GameObject tempObject;
		for (int i = this.handler.object.size() - 1; i >= 0 ; i--) {
			tempObject = this.handler.object.get(i);
			if (tempObject.getID() == ID.Enemy) this.handler.removeObject(tempObject);
		}
	}
	
	
	/**
	 * Növeli a játék szintjét
	 * És frissíti az enemy-ket
	 */
	public void levelUp() {
		this.level++;
		this.enemiesToKill++;
		player.setHealth(100);
		enemiesKilled = 0;
		this.clearEnemies();
		GameObject tempObject;
		for (int i = 0; i < this.handler.object.size(); i++) {
			tempObject = this.handler.object.get(i);
			if (tempObject.getID() == ID.Enemy) {
				
			}
		}

		this.addEnemies(this.enemiesToKill);
	}
	
	public int getLevel() { return this.level; }
	public void setLevel(int level) { this.level = level; }
	public int getCash() { return this.cash; }
	public void setCash(int score) { this.cash = score; }
	public int getScore() { return this.score; }
	public int getEnemiesToKill() { return this.enemiesToKill; }
	
}
