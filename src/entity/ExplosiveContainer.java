package entity;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import game.Game;
import graphics.Sprite;

public class ExplosiveContainer extends Entity {

	public static final BufferedImage explosiveBarrelImg = Sprite.explosiveBarrel.getImage();
	
	private int w = explosiveBarrelImg.getWidth();
	private int h = explosiveBarrelImg.getHeight();
	
	public static final int explosiveRange = 64;
	public float explosiveDamage = 5f;

	public static final Dimension containerDim = new Dimension(32, 32);

	public ExplosiveContainer(int x, int y, Game game) {
		super(x, y, game);
	}

	public void tick() {

	}

	public void render(Graphics g) {
		g.drawImage(explosiveBarrelImg, (int) x, (int) y, null);
	}

	public Rectangle getBounds() {
		return new Rectangle((int) x, (int) y, w, h);
	}

}
