package entity;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import game.Game;

public class Bullet extends Projectile {
	
	private int w, h;

	private static final float SPEED = 15.0f;

	public Bullet(int x, int y, float dmg, int mx, int my, Game game) {
		super(x, y, dmg, mx, my, game);
		w = 4;
		h = 4;
	}

	public void tick() {
		move(SPEED);
	}

	public void render(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect((int) x, (int) y, 4, 4);
	}

	public Rectangle getBounds() {
		return new Rectangle((int) x, (int) y, w, h);
	}
}
