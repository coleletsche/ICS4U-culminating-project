package entity;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

import game.Game;

public class FastEnemy extends Mob {
	
	private float speed = 1.5f + (new Random().nextFloat()) * (3f - 1.5f);
	
	private int w = 32, h = 32;
	
	public static final int coinsDrop = 2;
	public static final float chanceToSpawnMedKit = 2f;
	
	public static final Color miniMapColour = Color.CYAN;
	
	
	public FastEnemy(int x, int y, float health, float maxHealth, float damage, Game game) {
		super(x, y, health, maxHealth, damage, game);
	}

	public void tick() {
		
		move(speed);
		
		health = clamp(health, -1f, maxHealth);
	}

	public void render(Graphics g) {
		
		g.setColor(Color.CYAN);
		g.fillRect((int)x, (int)y, w, h);
		
		// Enemy health bar
		g.setColor(Color.RED);
		g.fillRect((int)x,(int) y-8, w, 4);
		
		g.setColor(Color.GREEN);
		g.fillRect((int)x, (int)y-8, (int)(getPercentageHealth() * w), 4);
		
	}

	public Rectangle getBounds() {
		return new Rectangle((int) x, (int)y, w, h);
	}
	
}
