package play_state;

import game_files.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class HUD {

	private Player player;
	private Handler handler;
	private Level level;
	//A célkereszt képe
	private BufferedImage aimImg;
	//A fegver képe
	private BufferedImage gunImg;
	
	
	/**
	 * Az osztály konstruktora
	 * @param player A játék játékosa
	 * @param handler A GameObject-ek kezelõje
	 * @param level A játék szintje
	 */
	public HUD(Player player, Handler handler, Level level) {
		this.player = player;
		this.level = level;
		this.handler = handler;
		
		try {
		    this.aimImg = ImageIO.read(new File("aim.png"));
		    this.gunImg = ImageIO.read(new File("gun.png"));
		} catch (IOException e) {
			System.out.println("Nem sikerult beolvasni a kepet");
		}
	}
	
	
	/**
	 * Kirajzolja a HUD-ot
	 * @param g A használt Graphics osztály
	 */
	public void render(Graphics g) {
		
		//draw healthbar
		double green = Game.map(player.getHealth(), 0, 100, 0, 255);
		double red = Game.map(player.getHealth(), 0, 100, 255, 0);
		g.setColor(new Color((int)red, (int)green, 0));
		double h = Game.map(player.getHealth(), 0, 100, 0, 300);
		g.fillRect(Game.WIDTH - 330, 10, (int)h, 30);
		g.setColor(Color.white);
		g.drawRect(Game.WIDTH - 330, 10, 300, 30);
		
		//draw aim
		g.drawImage(aimImg, Game.WIDTH / 2 - 15, Game.HEIGHT / 2 - 15, 30, 30, null);
		
		//draw map
		g.setColor(new Color(118, 123, 135));
		g.drawOval(20, Game.HEIGHT - (60 + 150), 150, 150);
		for (int i = 0; i < handler.object.size(); i++) {
			GameObject to = handler.object.get(i);
			if (to.getID() == ID.Enemy) {
				double toDist = Math.sqrt(Math.pow((to.getX() - player.getX()), 2) + Math.pow((to.getY() - player.getY()), 2));
				//System.out.println(toDist + " " + to.getX() + " " + player.getX() + " " + to.getY() + " " + player.getY());
				double toDirX = (to.getX() - player.getX()) / toDist;
				double toDirY = (to.getY() - player.getY()) / toDist;
				double angle = Math.atan2(player.getDirY(), player.getDirX()) - Math.atan2(toDirY, toDirX);
				if (toDist <= Camera.viewDistance) {
					g.setColor(new Color((int)Game.map(toDist, 0, Camera.viewDistance * 2, 255, 0), 0, (int)Game.map(toDist, 0, Camera.viewDistance * 2, 0, 255)));
					double t = Game.map(toDist, 0, Camera.viewDistance, 0, 1);
					g.fillOval(20 + (150 / 2) + (int)(75 * Math.sin(-angle) * t) - 15, Game.HEIGHT - (60 + 75 + (int)(75 * Math.cos(-angle) * t)) - 15, 30, 30);
				} else {
					g.setColor(Color.blue);
					g.fillOval(20 + (150 / 2) + (int)(75 * Math.sin(-angle)) - 15, Game.HEIGHT - (60 + 75 + (int)(75 * Math.cos(-angle))) - 15, 30, 30);
				}
			}
		}
		g.setColor(new Color(50, 168, 82));
		g.fillOval(20 + 75 - 10, Game.HEIGHT - (60 + 75) - 10, 20, 20);
		
		//draw weapon bar
		g.setColor(Color.blue);
		double w = Game.map(player.getGun().getCounter(), 0, player.getGun().getReload(), 0, 100);
		g.fillRect(Game.WIDTH - 330, 50, (int)w, 10);
		g.setColor(Color.white);
		g.drawRect(Game.WIDTH - 330, 50, 100, 10);
		
		
		//draw gun
		g.drawImage(gunImg, Game.WIDTH / 2 - (int)(gunImg.getWidth() * 1.3 / 2), Game.HEIGHT - (int)(gunImg.getHeight() * 1.3) - 30, (int)(gunImg.getWidth() * 1.3), (int)(gunImg.getHeight() * 1.3), null);
		
		//write level, score, cash enemiesToKill
		g.setFont(new Font("arial", 0, 20));
		g.drawString("Level: " + level.getLevel(), 20, 30);
		g.drawString("Score: " + level.getScore(), 20, 60);
		g.drawString("Cash: " + level.getCash(), 20, 90);
		g.drawString("Kill " + level.getEnemiesToKill() + " enemies!", 20, 120);

	}
	
	
}
