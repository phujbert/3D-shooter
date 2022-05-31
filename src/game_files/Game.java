package game_files;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Game /*extends Canvas*/ extends JPanel implements Runnable, KeyListener{
	
	private static final long serialVersionUID = -6109457652324010932L;
	
	//a program szélesége, magassága
	public static final int WIDTH = 1280, HEIGHT = WIDTH / 16 * 9;
	
	//egy szál
	private Thread thread;
	//változó, hogy fut-e a program
	private boolean running = false;
	
	//az ablak
	private Window window;
	
	//a GameStateManager ami a Gamestate-eket kezeli
	private GameStateManager gsm;
	
	/**
	 * A Game osztály konstruktora
	 */
	public Game() {
		this.setFocusable(true);
		this.addKeyListener(this);
		window = new Window(WIDTH, HEIGHT, "Game", this);
		this.gsm = new GameStateManager();
	}

	/**
	 * Elindít egy új szálat
	 */
	public synchronized void start() {
		thread = new Thread(this);
		thread.start();
		running = true;
	}
	
	/**
	 * Befejezi a szálat
	 */
	public synchronized void stop() {
		try {
			thread.join();
			running = false;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * A game loop
	 * 60 másodpercenként meghívja a tick metódust
	 * És annyiszor rajzolja újra a képernyõt ahányszor csak tudja
	 */
	public void run() {
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int updates = 0;
		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while(delta >= 1) {
				tick();
				updates++;
				delta--;
			}
			if (running) {
				//render();
				repaint(0, 0, Game.WIDTH, Game.HEIGHT);

			}
			
			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				JFrame frame = this.window.getJFrame();
				frame.setTitle("UPS: " + updates);
				updates = 0;
			}
		}
		stop();
		
	}
	
	/**
	 * Meghívja a GameStateManager tick metódusát
	 */
	private void tick() {
		gsm.tick();
	}
	
	/*private void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		
		
		g.setColor(Color.black);
		g.fillRect(0,  0, WIDTH, HEIGHT);
		
		gsm.render(g);		
		
		g.dispose();
		bs.show();
		
	}*/
	
	/**
	 * Meghívja a GameStateManager render metódusat
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.black);
		g.fillRect(0,  0, WIDTH, HEIGHT);
		
		gsm.render(g);
	}
	
	/**
	 * Bezár egy megadott értéket a megadott intervallumba
	 * @param var Az érték
	 * @param min Az intervallum eleje
	 * @param max Az intervallum vége
	 * @return A bezárt érték
	 */
	public static int clamp(int var, int min, int max) {
		if (var < min) return min;
		else if (var > max) return max;
		else return var;
	}
	
	/**
	 * Ha kisebb az érték egy minimum értéknél, akkor a minumum értékkel tér vissza
	 * @param var A megadott érték
	 * @param min A minimum érték
	 * @return Ha var kisebb min, akkor a minimum érték, egyébként a megadott érték
	 */
	public static int min(int var, int min) {
		if (var < min) return min;
		else return var;
	}
	
	/**
	 * Újraértékel egy megadott értéket az új intervallumon
	 * @param val A megadott érték
	 * @param a Az eredeti intervallum alja
	 * @param b Az eredeti intervallum teteje
	 * @param c Az új intervallum alja
	 * @param d Az új intervallum teteje
	 * @return Az új érték
	 */
	public static double map(double val, double a, double b, double c, double d) {
		return c + (d - c) * ((val - a) / (b - a));
	}
	
	/**
	 * Létrehoz egy új játékot
	 * @param args
	 */
	public static void main(String[] args) {
		new Game();
	}

	public void keyTyped(KeyEvent e) {}

	/**
	 * Meghívja a GameStateManager keyPressed metódusát
	 */
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		gsm.keyPressed(key);
	}

	/**
	 * Meghívja a GameStateManager keyReleased metódusát
	 */
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		gsm.keyreleased(key);
	}
	
 }
