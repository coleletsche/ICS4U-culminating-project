package entity;

import game.Game;

public abstract class Projectile extends Entity {
	
	// Main projectile class

	private float xVel = 0;
	private float yVel = 0;

	private float angle;

	public float damage;
	
	public static final int delay = 10;

	public Projectile(int x, int y, float dmg, int mx, int my, Game game) {
		super(x, y, game);
		
		// Find the angle between the mouse click and the player
		angle = (float) Math.atan2(y - my, x - mx);
		this.damage = dmg;
	}

	// move the projectile at a given speed
	public void move(float speed) {
		xVel = (float) ((-speed) * Math.cos(angle));
		yVel = (float) ((-speed) * Math.sin(angle));

		x += xVel;
		y += yVel;
	}
}
