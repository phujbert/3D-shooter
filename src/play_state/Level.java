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
	
	//A jelenlegi szintje a j�t�knak
	private int level = 1;
	//A j�t�kos p�nzze
	private int cash = 0;
	//A j�t�kos pontja
	private int score = 0;
	//Sz�ml�l� a meg�lt a szinten meg�lt enemy-knek
	private int enemiesKilled = 0;
	//Ennyi enemy-t kell meg�lni a szinten
	private int enemiesToKill = 10;
	
	
	/**
	 * Az oszt�ly konstruktora
	 * Inicializ�lja az oszt�ly v�ltoz�it
	 * �s hozz�ad enemiesToKill sz�m� enemy-t
	 * @param gsm A GameStateManager
	 * @param handler A GameObject-ek kezel�je
	 * @param grid A grid amin zajlik a j�t�k
	 * @param camera A kamera, amin kereszt�l a j�t�kos l�tja a j�t�kot
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
	 * Friss�ti a level �llapot�t
	 * Ha egy GameObject �lete 0 vagy az al� cs�kken akkor elt�vol�tja azt a handler object t�mbj�b�l
	 * Ha a Player �lete cs�kken le 0 vagy az al� akkor kil�p a PlayState-b�l
	 * Ha meg�lt�nk megfelel� menny�s�g� enemy-t akkor n�veli eggyel az oszt�ly level v�ltoz�j�t
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
	 * L�trehoz egy �j enemy-t, inicializ�lja azt, �s hozz�adja a j�t�khoz
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
	 * T�rli az �sszes enemy-t a handler object t�mbj�b�l
	 */
	public void clearEnemies() {
		GameObject tempObject;
		for (int i = this.handler.object.size() - 1; i >= 0 ; i--) {
			tempObject = this.handler.object.get(i);
			if (tempObject.getID() == ID.Enemy) this.handler.removeObject(tempObject);
		}
	}
	
	
	/**
	 * N�veli a j�t�k szintj�t
	 * �s friss�ti az enemy-ket
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
