package play_state;

import skillshop_state.SkillShopState;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.Random;

import game_files.GameState;
import game_files.GameStateManager;

public class PlayState extends GameState {
	
	private Handler handler;
	private Player player;
	private Camera camera;
	private Grid grid;
	private HUD hud;
	private Random random;
	private Level level;
	
	//Tárolja, hogy épp melyik billentyûzet van lenyomott állapotban
	private boolean keyDown[];
	//Segédváltozó a forgatás mértékéhez
	private double rot = 1;
	
	public PlayState(GameStateManager gsm) {
		super(gsm);
	}

	/**
	 * Inicializálja az osztály változóit
	 */
	public void init() {
		random = new Random();
		grid = new Grid(10, 10);
		handler = new Handler();
		camera = new Camera( grid, handler);
		this.spawnPlayer();
		handler.addObject(player);
		camera.setPlayer(player);
		level = new Level(gsm, handler, grid, camera);
		hud = new HUD(player, handler, level);
		
		keyDown = new boolean[65536];
		for (int i = 0; i < keyDown.length; i++) keyDown[i] = false;
	}

	/**
	 * Frissíti az osztály állapotát
	 */
	public void tick() {
		level.tick();
		camera.tick();
		handler.tick();
		this.managePlayerInput();
	}

	/**
	 * Kirajzolja a PlayState során látható dolgokat
	 */
	public void render(Graphics g) {
		camera.render(g);
		hud.render(g);
	}

	/**
	 * Lekezeli a PlayStae alatti key inputot
	 */
	public void keyPressed(int key) {
		keyDown[key] = true;
		
		//back to the menu state
		if (key == KeyEvent.VK_ESCAPE) gsm.states.pop();
		
		//enter skillshop state
		if (key == KeyEvent.VK_E) {
			for (int i = 0; i < keyDown.length; i++) keyDown[i] = false;
			gsm.states.push(new SkillShopState(gsm, player, level));
		}
	}

	/**
	 * Lekezeli a PlayStae alatti key inputot
	 */
	public void keyReleased(int key) {
		keyDown[key] = false;
	}
	
	/**
	 * Lekezeli a PlayStae alatti key inputot
	 */
	public void managePlayerInput() {
		if (keyDown[KeyEvent.VK_W] || keyDown[KeyEvent.VK_D]) player.setMoveSpeed(player.getAbsoluteSpeed());
		else if (keyDown[KeyEvent.VK_S] || keyDown[KeyEvent.VK_A]) player.setMoveSpeed(-player.getAbsoluteSpeed());
		else player.setMoveSpeed(0);
		
		if (keyDown[KeyEvent.VK_LEFT]) { player.setRotSpeed(-0.003 * rot); if (rot <= 15)rot++; }
		else if (keyDown[KeyEvent.VK_RIGHT]) { player.setRotSpeed(0.003 * rot); if (rot <= 15)rot++; }
		else { player.setRotSpeed(0); rot = 1; }
		
		if ((keyDown[KeyEvent.VK_D] && keyDown[KeyEvent.VK_W]) || (keyDown[KeyEvent.VK_A] && keyDown[KeyEvent.VK_S]))
			player.setSideMove(Math.PI / 4);
		else if ((keyDown[KeyEvent.VK_A] && keyDown[KeyEvent.VK_W]))
			player.setSideMove(-Math.PI / 4);
		else if ((keyDown[KeyEvent.VK_D] && keyDown[KeyEvent.VK_S]))
			player.setSideMove(3 * Math.PI / 4);
		else if (keyDown[KeyEvent.VK_D] || keyDown[KeyEvent.VK_A])
			player.setSideMove(Math.PI / 2);
		else player.setSideMove(0);
		
		if (keyDown[KeyEvent.VK_UP]) if (player.getGun().getLoaded()) player.getGun().shoot();
	}
	
	/**
	 * Inicializálja a player változót
	 */
	private void spawnPlayer() {
		int x = 0, y = 0;
		do {
			x = random.nextInt(10);
			y = random.nextInt(10);
		} while (grid.getCell(y, x) != 0);
		player = new Player(x + 0.5, y + 0.5, grid, camera, handler);
	}

}
