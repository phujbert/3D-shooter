package game_files;

import java.awt.Graphics;
import java.util.Stack;

import menu_state.MenuState;

public class GameStateManager {

	/**
	 * A jat�k�llapotokat t�rol� collection
	 */
	public Stack<GameState> states;
	
	/**
	 * Az oszt�ly konstruktora
	 */
	public GameStateManager() {
		this.states = new Stack<GameState>();
		this.states.push(new MenuState(this));
	}
	
	/**
	 * Megh�vja a state Stack legfels� elem�nek a tick met�dus�t
	 */
	public void tick() {
		this.states.peek().tick();
	}
	
	/**
	 * Megh�vja a state Stack legfels� elem�nek a render met�dus�t
	 */
	public void render(Graphics g) {
		this.states.peek().render(g);
	}
	
	/**
	 * Megh�vja a state Stack legfels� elem�nek a keyPressed met�dus�t
	 */
	public void keyPressed(int key) {
		this.states.peek().keyPressed(key);
	}
	
	/**
	 * Megh�vja a state Stack legfels� elem�nek a keyReleased met�dus�t
	 */
	public void keyreleased(int key) {
		this.states.peek().keyReleased(key);
	}
	
	
	
	
	
}
