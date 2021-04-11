package entity;

import java.awt.Graphics;
import java.awt.Rectangle;

import game.Game;

public abstract class Entity {
	
	
	// Entity base Class

	
	// x and y's
	public float x, y;

	public Game game;

	public Entity(int x, int y, Game game) {
		this.x = x;
		this.y = y;
		this.game = game;
	}

	
	// All instances of the Entity class will have these methods
	public abstract void tick();

	public abstract void render(Graphics g);

	public abstract Rectangle getBounds();
	
	public static float clamp(float var, float min, float max) {
		if (var >= max)
			return var = max;
		else if (var <= min)
			return var = min;
		else
			return var;
	}
}
