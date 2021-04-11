package graphics;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import game.Game;

public class HUD {

	private Game game;

	// The original percent of the player health; 
	double percentPlayerHealth = 1;
	double percentPlayerShield = 1;
	double percentPlayerEnergy = 1;

	
	// Fonts
	private Font font = new Font("arial", Font.BOLD, 12);
	
	private BufferedImage enemiesKilled = Sprite.enemiesKilledSmall.getImage();
	private BufferedImage wave = Sprite.waveSmall.getImage();
	private BufferedImage coinImg = Sprite.coins[0].getImage();

	private Rectangle healthShieldRec;

	
	// the opacity of the HUD
	private int healthOpacity = 255;

	public HUD(Game game) {
		this.game = game;
	}

	public void tick() {
		// Calculate percentage of health;
		percentPlayerHealth = game.player.getHealth() / game.player.getMaxHealth();
		percentPlayerShield = game.player.getShield() / game.player.getMaxShield();
		percentPlayerEnergy = game.player.getEnergy() / game.player.getMaxEnergy();

		healthShieldRec = new Rectangle(50 + game.camera.getX(), Game.HEIGHT - 91 + game.camera.getY(), Game.WIDTH, 41);

		// Check if the player is within the health rectangle
		if (game.player.getBounds().intersects(healthShieldRec)) {
			healthOpacity = 32;
		} else {
			healthOpacity = 255;
		}
	}

	public void render(Graphics g) {
		
		// White Layer
		g.setColor(new Color(255,255,255, healthOpacity));
		g.fillRect(Game.WIDTH / 2 - 302, Game.HEIGHT - 77, 604, 29);
		g.fillRect(Game.WIDTH / 2 - 252, Game.HEIGHT - 93, 504, 20);
		g.fillRect(48, Game.HEIGHT - 77, 104, 29);
		g.fillRoundRect(Game.WIDTH - 162, Game.HEIGHT - 107, 154, 79, 10, 10);

		
		// Black Layer
		g.setColor(new Color(0, 0, 0, healthOpacity));
		g.fillRect(Game.WIDTH / 2 - 300, Game.HEIGHT - 75, 600, 25);
		g.fillRect(Game.WIDTH / 2 - 250, Game.HEIGHT - 91, 500, 16);
		g.fillRect(50, Game.HEIGHT - 75, 100, 25);
		g.fillRoundRect(Game.WIDTH - 160, Game.HEIGHT - 105, 150, 75, 10, 10);

		
		// Gray Layer
		g.setColor(new Color(128, 128, 128, healthOpacity));
		g.fillRect(Game.WIDTH / 2 - 295, Game.HEIGHT - 70, 590, 15);
		g.fillRect(Game.WIDTH / 2 - 246, Game.HEIGHT - 88, 492, 12);
		g.fillRect(55, Game.HEIGHT - 70, 90, 15);
		
		// Images
		g.drawImage(coinImg, Game.WIDTH - 150, Game.HEIGHT - 95, null);
		g.drawImage(wave, Game.WIDTH - 150, Game.HEIGHT - 75, null);
		g.drawImage(enemiesKilled, Game.WIDTH - 150, Game.HEIGHT - 50, null);
		
		// Info
		g.setColor(new Color(255,255,255, healthOpacity));
		g.setFont(new Font("arial", Font.BOLD, 16));
		g.drawString("=         " + game.coins, Game.WIDTH - 125, Game.HEIGHT - 85);
		g.drawString("=         " + game.wave, Game.WIDTH - 125, Game.HEIGHT - 60);
		g.drawString("=         " + game.enemiesKilled, Game.WIDTH - 125, Game.HEIGHT - 35);

		// Drawing health bar
		if (percentPlayerHealth <= 0.1)
			g.setColor(new Color(139, 0, 0, healthOpacity));
		else if (percentPlayerHealth <= 0.25 && percentPlayerHealth > 0.1)
			g.setColor(new Color(255, 0, 0, healthOpacity));
		else if (percentPlayerHealth <= 0.75 && percentPlayerHealth > 0.25)
			g.setColor(new Color(255, 140, 0, healthOpacity));
		else
			g.setColor(new Color(0, 255, 0, healthOpacity));
		g.fillRect(Game.WIDTH / 2 - 295, Game.HEIGHT - 70, (int) (percentPlayerHealth * 590), 15);

		// Drawing Shield
		g.setColor(new Color(0, 255, 255, healthOpacity));
		g.fillRect(Game.WIDTH / 2 - 246, Game.HEIGHT - 88, (int) (percentPlayerShield * 492), 12);

		// Drawing Energy
		g.setColor(new Color(255, 255, 0, healthOpacity));
		g.fillRect(55, Game.HEIGHT - 70, (int) (percentPlayerEnergy * 90), 15);

		// Stats for health
		g.setColor(Color.WHITE);
		g.setFont(font);
		g.drawString(Math.round(game.player.getHealth()) + "/" + Math.round(game.player.getMaxHealth()),
				Game.WIDTH / 2 - 20, Game.HEIGHT - 58);

		// Stats for Shield
		g.setColor(Color.WHITE);
		g.setFont(font);
		g.drawString(Math.round(game.player.getShield()) + "/" + Math.round(game.player.getMaxShield()),
				Game.WIDTH / 2 - 20, Game.HEIGHT - 77);
	}

}
