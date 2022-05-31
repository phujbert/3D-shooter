package game_files;

import java.awt.Dimension;

import javax.swing.JFrame;

public class Window {

	//A JFrame amin megjelenik a program
	private JFrame frame;

	/**
	 * Az osztály konstruktora
	 * @param width Az ablak szélessége
	 * @param height Az ablak magassága
	 * @param title Az ablak címe
	 * @param game A játék
	 */
	public Window(int width, int height, String title, Game game) {
		frame = new JFrame(title);
		
		frame.setPreferredSize(new Dimension(width, height));
		frame.setMaximumSize(new Dimension(width, height));
		frame.setMinimumSize(new Dimension(width, height));
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.add(game);
		frame.setVisible(true);
		game.start();
		
	}
	
	public JFrame getJFrame() { return this.frame; }
}
