package graphics;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SpriteSheet {

	
	// This class is used to parse multiple sprite from a larger img file
	// Sprite class with then parse individual sprite
	
	private String path;
	public final int SIZE;
	public int[] pixels;

	
	// SpriteSheet in static memory
	
	public static SpriteSheet tiles = new SpriteSheet("/res/Sprite Sheets/SpriteSheetBeta.png", 1280);
	public static SpriteSheet entites = new SpriteSheet("/res/Sprite Sheets/EntitySpriteSheet.png", 1280);
	public static SpriteSheet mob = new SpriteSheet("/res/Sprite Sheets/MobSpriteSheets.png", 1280);
	public static SpriteSheet coins = new SpriteSheet("/res/Sprite Sheets/coinSpriteSheet.png", 256);

	
	// Constructor
	public SpriteSheet(String path, int size) {
		this.path = path;
		SIZE = size;
		pixels = new int[SIZE * SIZE];
		load();
	}
	
	// Load the bufferedImage
	private void load() {
		try {
			BufferedImage image = ImageIO.read(SpriteSheet.class.getResource(path));
			int w = image.getWidth();
			int h = image.getHeight();
			image.getRGB(0, 0, w, h, pixels, 0, w);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
