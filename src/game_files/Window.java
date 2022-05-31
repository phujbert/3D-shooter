package game_files;

import java.awt.Dimension;

import javax.swing.JFrame;

public class Window {

	//A JFrame amin megjelenik a program
	private JFrame frame;

	/**
	 * Az oszt�ly konstruktora
	 * @param width Az ablak sz�less�ge
	 * @param height Az ablak magass�ga
	 * @param title Az ablak c�me
	 * @param game A j�t�k
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
