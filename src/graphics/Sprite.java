package graphics;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Sprite {
	
	private BufferedImage img;
	
	private int x, y;
	
	public int[] pixels;
	
	// Sprites into static memory
	
	public static Sprite lightStone = new Sprite(32, 0, 0, SpriteSheet.tiles);
	public static Sprite stone = new Sprite(32, 1, 0, SpriteSheet.tiles);
	public static Sprite darkStone = new Sprite(32, 2, 0, SpriteSheet.tiles);
	
	public static Sprite grass_and_stone = new Sprite(32, 0, 1, SpriteSheet.tiles);
	public static Sprite mossyStone = new Sprite(32, 0, 2, SpriteSheet.tiles);
	
	public static Sprite medKit = new Sprite(32, 0, 0, SpriteSheet.entites);
	
	public static Sprite explosiveBarrel = new Sprite("/res/barrel.png");
	
	public static Sprite title = new Sprite("/res/title.png");
	
	public static Sprite instruction = new Sprite("/res/Instructions.png");
	
	public static Sprite wave = new Sprite("/res/levelImage.png");
	public static Sprite waveSmall = new Sprite("/res/levelImageSmall.png");
	
	public static Sprite player = new Sprite("/res/player1.png");
	
	public static Sprite medic = new Sprite("/res/medic.png");
	
	public static Sprite titleScreen = new Sprite("/res/titleScreen.png");
	
	public static Sprite enemiesKilled = new Sprite("/res/enemiesKilled.png");
	public static Sprite enemiesKilledSmall = new Sprite("/res/enemiesKilledSmall.png");
	
	public static Sprite[] playerSprites = {
			new Sprite("/res/player1.png"),
			new Sprite("/res/player2.png"),
	};
	
	public static Sprite[] slimeEnemies = {
			new Sprite(32, 0, 0, SpriteSheet.mob),
			new Sprite(32, 1, 0, SpriteSheet.mob),
			new Sprite(32, 2, 0, SpriteSheet.mob),
	};
	
	public static Sprite[] coins = {
			new Sprite(16, 0, 0, SpriteSheet.coins),
			new Sprite(16, 1, 0, SpriteSheet.coins),
			new Sprite(16, 2, 0, SpriteSheet.coins),
			new Sprite(16, 3, 0, SpriteSheet.coins),
			new Sprite(16, 4, 0, SpriteSheet.coins),
			new Sprite(16, 5, 0, SpriteSheet.coins),
			new Sprite(16, 6, 0, SpriteSheet.coins),
			new Sprite(16, 7, 0, SpriteSheet.coins),
			new Sprite(16, 8, 0, SpriteSheet.coins),
	};
	
	
	// Overload the constructor for parsing data from a sprite sheet or direct path
	
	public Sprite(String path) {
		img = loadImage(path);
	}
	
	
	// geting pixelData
	public Sprite(int size, int x, int y, SpriteSheet sheet) {
		img = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
		int[] pixels = ((DataBufferInt) img.getRaster().getDataBuffer()).getData();
		this.x = x * size;
		this.y = y * size;
		for (int yy = 0; yy < size; yy++)
			for (int xx = 0; xx < size; xx++)
				pixels[xx + yy * size] = sheet.pixels[(xx + this.x) + (yy + this.y) * sheet.SIZE];
	}
	
	public BufferedImage loadImage(String path) {
		try {
			img = ImageIO.read(Sprite.class.getResource(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return img;
	}
	
	public BufferedImage getImage() {
		return img;
	}

}
