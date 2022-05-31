package end_state;

import leaderboard_state.LeaderBoardState;
import leaderboard_state.User;
import game_files.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class EndState extends GameState implements ActionListener {
	
	//JFrame a felhasználónév bekérdezéséhez
	private JFrame f;
	//ide kell megadni nevet
	private JTextField tf;
	//az OK gomb
	private JButton b;
	//JTextfield-et, JButton-t tartalmazó panel
	private JPanel p;
	//a név
	private String userName = "blank";
	//az elért pontszám
	private int score;
	

	/**
	 * Az osztély konstruktora
	 * @param gsm A GameStateManager
	 * @param score A játék végén a játékos által elért pontszám
	 */
	public EndState(GameStateManager gsm, int score) {
		super(gsm);
		this.score = score;
	}

	/**
	 * Inicializálja a state objektumait
	 */
	public void init() {
		f = new JFrame("Input user name");
		p = new JPanel();
		tf = new JTextField();
		tf.setColumns(20);
		
		ActionListener a1 = this;
		b = new JButton("OK");
		b.setActionCommand("enter");

		b.addActionListener(a1);
		p.add(b);
		p.add(tf);
		f.add(p, BorderLayout.NORTH);
		f.pack();
		f.setVisible(true);
	}
	public void tick() {}

	/**
	 * Kiírja a játékos által elért pontszámot
	 */
	public void render(Graphics g) {
		g.setColor(Color.white);
		g.setFont(new Font("arial", 0, 40));
		g.drawString("Your score: " + this.score, Game.WIDTH / 2 - 50, Game.HEIGHT / 2 + 10);

	}

	public void keyPressed(int key) {}

	public void keyReleased(int key) {}
	

	/**
	 * Az OK gomb lenyomására a leaderboard state-re vált
	 */
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("enter")) {
			userName = tf.getText();
			f.dispose();
			this.gsm.states.pop();
			this.gsm.states.push(new LeaderBoardState(gsm, new User(userName, score)));
		}
	}
		
	
	
	

}
