package game_files;

import java.awt.Graphics;
import java.util.Stack;

import menu_state.MenuState;

public class GameStateManager {

	/**
	 * A jatékállapotokat tároló collection
	 */
	public Stack<GameState> states;
	
	/**
	 * Az osztály konstruktora
	 */
	public GameStateManager() {
		this.states = new Stack<GameState>();
		this.states.push(new MenuState(this));
	}
	
	/**
	 * Meghívja a state Stack legfelsõ elemének a tick metódusát
	 */
	public void tick() {
		this.states.peek().tick();
	}
	
	/**
	 * Meghívja a state Stack legfelsõ elemének a render metódusát
	 */
	public void render(Graphics g) {
		this.states.peek().render(g);
	}
	
	/**
	 * Meghívja a state Stack legfelsõ elemének a keyPressed metódusát
	 */
	public void keyPressed(int key) {
		this.states.peek().keyPressed(key);
	}
	
	/**
	 * Meghívja a state Stack legfelsõ elemének a keyReleased metódusát
	 */
	public void keyreleased(int key) {
		this.states.peek().keyReleased(key);
	}
	
	
	
	
	
}
