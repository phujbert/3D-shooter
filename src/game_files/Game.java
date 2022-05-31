package game_files;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Game /*extends Canvas*/ extends JPanel implements Runnable, KeyListener{
	
	private static final long serialVersionUID = -6109457652324010932L;
	
	//a program sz�les�ge, magass�ga
	public static final int WIDTH = 1280, HEIGHT = WIDTH / 16 * 9;
	
	//egy sz�l
	private Thread thread;
	//v�ltoz�, hogy fut-e a program
	private boolean running = false;
	
	//az ablak
	private Window window;
	
	//a GameStateManager ami a Gamestate-eket kezeli
	private GameStateManager gsm;
	
	/**
	 * A Game oszt�ly konstruktora
	 */
	public Game() {
		this.setFocusable(true);
		this.addKeyListener(this);
		window = new Window(WIDTH, HEIGHT, "Game", this);
		this.gsm = new GameStateManager();
	}

	/**
	 * Elind�t egy �j sz�lat
	 */
	public synchronized void start() {
		thread = new Thread(this);
		thread.start();
		running = true;
	}
	
	/**
	 * Befejezi a sz�lat
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
	 * 60 m�sodpercenk�nt megh�vja a tick met�dust
	 * �s annyiszor rajzolja �jra a k�perny�t ah�nyszor csak tudja
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
	 * Megh�vja a GameStateManager tick met�dus�t
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
	 * Megh�vja a GameStateManager render met�dusat
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.black);
		g.fillRect(0,  0, WIDTH, HEIGHT);
		
		gsm.render(g);
	}
	
	/**
	 * Bez�r egy megadott �rt�ket a megadott intervallumba
	 * @param var Az �rt�k
	 * @param min Az intervallum eleje
	 * @param max Az intervallum v�ge
	 * @return A bez�rt �rt�k
	 */
	public static int clamp(int var, int min, int max) {
		if (var < min) return min;
		else if (var > max) return max;
		else return var;
	}
	
	/**
	 * Ha kisebb az �rt�k egy minimum �rt�kn�l, akkor a minumum �rt�kkel t�r vissza
	 * @param var A megadott �rt�k
	 * @param min A minimum �rt�k
	 * @return Ha var kisebb min, akkor a minimum �rt�k, egy�bk�nt a megadott �rt�k
	 */
	public static int min(int var, int min) {
		if (var < min) return min;
		else return var;
	}
	
	/**
	 * �jra�rt�kel egy megadott �rt�ket az �j intervallumon
	 * @param val A megadott �rt�k
	 * @param a Az eredeti intervallum alja
	 * @param b Az eredeti intervallum teteje
	 * @param c Az �j intervallum alja
	 * @param d Az �j intervallum teteje
	 * @return Az �j �rt�k
	 */
	public static double map(double val, double a, double b, double c, double d) {
		return c + (d - c) * ((val - a) / (b - a));
	}
	
	/**
	 * L�trehoz egy �j j�t�kot
	 * @param args
	 */
	public static void main(String[] args) {
		new Game();
	}

	public void keyTyped(KeyEvent e) {}

	/**
	 * Megh�vja a GameStateManager keyPressed met�dus�t
	 */
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		gsm.keyPressed(key);
	}

	/**
	 * Megh�vja a GameStateManager keyReleased met�dus�t
	 */
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		gsm.keyreleased(key);
	}
	
 }
