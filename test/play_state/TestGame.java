package play_state;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import game_files.GameStateManager;

public class TestGame {
	
	private Grid grid;
	private Player player;
	private Enemy enemy;
	private Camera camera;
	private Handler handler;
	
	
	@Before
	public void init() {
		grid = new Grid(1, 2);
		grid.setCell(0, 0, 0);
		grid.setCell(0, 1, 1);	
		handler = new Handler();
		player = new Player(0.5, 0.5, grid, camera, handler);
		player.setDirX(1);
		player.setDirY(0);
		camera = new Camera(grid, handler);
		camera.setPlayer(player);

		
	}

	@Test
	public void testGun() {
		player.getGun().shoot();
		GameObject b = handler.object.get(0);
		b.move();
		assertTrue((b.getX() == 0.6) && (b.getY() == 0.5));
	}
	
	@Test
	public void testBulletHitsWall() {
		player.getGun().shoot();
		GameObject b = handler.object.get(0);
		b.setMoveSpeed(0.5);
		b.move();
		assertTrue(grid.getCell(0, 1) == 0 && b.getHealth() == 0);
	}
	
	@Test
	public void testRaycast() {
		camera.raycast(640);
		PixelCol p = camera.getPixelCol(0);
		assertTrue(p.getHit() == grid.getCell(0, 1));
	}
	
	@Test
	public void testDrawObject() {
		GameObject e = new Enemy(2.5, 1.5, grid, camera, handler);
		player.setRotSpeed(0.07);
		player.rotate();
		camera.drawObject(e);
		int ty = (int)e.getTransformY();
		assertTrue(ty == 14);
	}
	
	@Test
	public void testPlayerCollision() {
		handler.addObject(new Bullet(0.51, 0.5, ID.EnemyBullet, grid, camera, handler));
		player.collision();
		assertTrue(player.getHealth() == 95);
	}
	
	@Test
	public void testEnemyCollision() {
		Enemy e = new Enemy(2.5, 1.5, grid, camera, handler);
		handler.addObject(new Bullet(2.51, 1.5, ID.PlayerBullet, grid, camera, handler));
		e.collision();
		assertTrue(e.getHealth() == 0);
	}
	
	@Test
	public void testAddEnemy() {
		GameStateManager gsm = new GameStateManager();
		Level level = new Level(gsm, handler, grid, camera);
		level.clearEnemies();
		level.setLevel(3);
		level.addEnemies(1);
		GameObject e = handler.object.get(0);
		assertTrue(e.getHealth() == 3);
	}

}
