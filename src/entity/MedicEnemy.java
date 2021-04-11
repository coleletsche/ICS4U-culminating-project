package entity;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Random;

import game.Game;
import graphics.Sprite;

public class MedicEnemy extends Mob {

	private float speed = 0.1f + (new Random().nextFloat()) * (0.4f - 0.1f);

	private static final BufferedImage img = Sprite.medic.getImage();

	private int w, h;

	public static final int coinsDrop = 5;
	public static final float chanceToSpawnMedKit = 25f;
	
	// Range of healing
	public static final int range = 64;
	public static final float healthInc = 0.05f;

	public static final Color miniMapColour = new Color(255, 0, 0);

	public MedicEnemy(int x, int y, float health, float maxHealth, float damage, Game game) {
		super(x, y, health, maxHealth, damage, game);
		w = img.getWidth();
		h = img.getHeight();
	}

	public void tick() {
		
		health = clamp(health, -1f, maxHealth);

		move(speed);
	}

	public void render(Graphics g) {
		g.drawImage(img, (int) x, (int) y, null);

		// Enemy health bar
		g.setColor(Color.RED);
		g.fillRect((int) x, (int) y - 8, w, 4);

		g.setColor(Color.GREEN);
		g.fillRect((int) x, (int) y - 8, (int) (getPercentageHealth() * w), 4);
	}

	public Rectangle getBounds() {
		return new Rectangle((int) x, (int) y, w, h);
	}
	
	public Rectangle getHealingBounds() {
		return new Rectangle((int) x - range + w/2, (int) y - range + w/2, range*2, range*2);
	}

}
