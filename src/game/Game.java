package game;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.swing.JOptionPane;

import audio.MusicPlayer;
import entity.EntityHandler;
import entity.Player;
import graphics.HUD;
import graphics.Sprite;

public class Game extends Canvas implements Runnable {

	private static final long serialVersionUID = 1L;

	public static final int WIDTH = 960;
	public static final int HEIGHT = 566;

	public static final Dimension MapDim = new Dimension(1280, 1280);

	private double version = 1.4;
	private String title = "Survive";

	private Thread thread;
	private boolean isRunning = false;

	// DECALRING INSTANCES OF NEEDED CLASSES

	private Window window;
	public EntityHandler handler;
	public MusicPlayer bgMusic;
	public Player player = new Player(MapDim.width / 2 - 16, MapDim.height / 2 - 16, this);
	public static Level lvl = new Level(MapDim.width, MapDim.height);
	private HUD hud;
	public Camera camera;
	public Random r = new Random();
	private MiniMap miniMap;
	private Spawn spawn = new Spawn(this);
	private Menu menu;

	private BufferedImage aniImg = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
	private BufferedImage titleScreen = Sprite.titleScreen.getImage();
	private BufferedImage instructionScreen = Sprite.instruction.getImage();
	public BufferedImage lvlImg = lvl.getImg();
	
	public InputEvents input = new InputEvents(this);

	// MAIN VARS

	private int counter = 0;
	public int counter2 = 0;

	public int coins = 0;
	public int wave = 1;
	public int enemiesKilled = 0;
	
	public int highScore = 0;
	public int highestWave = 0;
	public int highestEnemiesKilled = 0;

	// Used for simplicity
	public enum GameState {
		Game, Menu, GameOver, AnimateIn, AnimateOut, TitleScreen, Pause, WaveTransition, Instructions, Options,
	};

	// Beginning GameState
	public GameState state = GameState.Menu;

	public Game() {
		
		// Instances of classes used in the game
		window = new Window(WIDTH, HEIGHT, title + " v " + version, this);
		menu = new Menu(this);
		camera = new Camera(0, 0);
		handler = new EntityHandler(this, player);
		hud = new HUD(this);
		miniMap = new MiniMap(5, 5, this);
		bgMusic = new MusicPlayer("/Music/menu.wav");
		
		// Add Event Listener
		this.addKeyListener(input);
		this.addMouseListener(input);
		this.addMouseListener(menu);
		this.addMouseMotionListener(menu);
	}

	
	// Starting the thread
	public void start() {
		isRunning = true;
		thread = new Thread(this);
		thread.start();
	}

	
	// Used to stop the game
	public void stop() {
		isRunning = false;
		try {
			thread.join();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	// Runnable thread inherit method
	public void run() {
		this.requestFocus();
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int frames = 0;
		int updates = 0;
		while (isRunning) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while (delta >= 1) {
				try {
					tick();
					render();
					updates++;
					frames++;
				} catch (RuntimeException e) {
				}
				delta--;
			}

			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				window.frame.setTitle(title + " v " + version + " | FPS: " + frames + " | Updates: " + updates);
				frames = 0;
				updates = 0;
			}
		}
		stop();
	}

	public void tick() {
		
		// Update methods for different states
		
		if (state == GameState.Game) {
			// Check for game over
			handler.tick();
			hud.tick();
			player.tick();
			camera.tick(player);
			input.tick();

			if (player.getHealth() <= 0) {
				highScore = coins > highScore ? coins : highScore;
				highestWave = wave > highestWave ? wave : highestWave;
				highestEnemiesKilled = enemiesKilled > highestEnemiesKilled ? enemiesKilled : highestEnemiesKilled;
				state = GameState.GameOver;
			}
			
			if (handler.getNumberOfEnemies() == 0){
				wave++;
				state = GameState.WaveTransition;
				counter2 = 0;
			}
			counter2 += 3;
		} else if (state == GameState.WaveTransition) {
			
			handler.tick();
			player.tick();
			camera.tick(player);
			hud.tick();
			
			counter2 += 2;
		} else if (state == GameState.Menu) {
			menu.tick();
			camera.tick(player);
			counter2 += 3;
		} else if (state == GameState.TitleScreen) {
			counter2 += 1;
		} else if (state == GameState.Pause) {

		}
	}

	public void render() {
		// Getting buffer
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			// The optimal amount of buffers in the highest number, but it
			// stresses resources
			this.createBufferStrategy(3);
			return;
		}

		
		// Getting graphics from buffer
		Graphics g = bs.getDrawGraphics();

		// for camera movement
		Graphics2D g2d = (Graphics2D) g;

		// for the transitions
		Graphics ani = aniImg.getGraphics();

		g.setColor(Color.WHITE);
		g.fillRect(0, 0, WIDTH, HEIGHT);

		
		// Rendering the opening screen
		if (state == GameState.TitleScreen) {

			if (counter2 >= 0 && counter2 <= 256) {
				g.setColor(new Color(0, 0, 0, 256 - counter2));
			} else if (counter2 >= 257 && counter2 <= 510) {
				g.setColor(new Color(0, 0, 0, counter2 % 256));
			} else {
				state = GameState.Menu;
				counter2 = 0;
				bgMusic.play();
			}
			g.drawImage(titleScreen, 0, 0, null);
			g.fillRect(0, 0, WIDTH, HEIGHT);
		}

		
		// Rendering the menu (Separate class)
		if (state == GameState.Menu) {

			g2d.translate(-camera.getX(), -camera.getY());
			g.drawImage(lvlImg, 0, 0, null);
			g2d.translate(camera.getX(), camera.getY());
			
			menu.render(g);

			if (counter2 >= 0 && counter2 <= 255) {
				g.setColor(new Color(0, 0, 0, 255 - counter2));
				g.fillRect(0, 0, WIDTH, HEIGHT);
			}
		}

		// Animate into the game
		if (state == GameState.AnimateIn) {

			g2d.translate(-camera.getX(), -camera.getY());
			g.drawImage(lvlImg, 0, 0, null);
			g2d.translate(camera.getX(), camera.getY());

			counter += 3;
			if (counter < 256) {
				menu.render(g);
				ani.setColor(new Color(0, 0, 0, counter));
			} else {
				state = GameState.WaveTransition;
				counter2 = 0;
			}
			ani.fillRect(0, 0, WIDTH, HEIGHT);
			g.drawImage(aniImg, 0, 0, null);
		}

		
		// Rendering when transitioning to different waves
		if (state == GameState.WaveTransition) {

			g2d.translate(-camera.getX(), -camera.getY());
			
			g.drawImage(lvlImg, 0, 0, null);
			player.render(g);
			handler.render(g);
			
			g2d.translate(camera.getX(), camera.getY());
			
			miniMap.render(g);
			hud.render(g);

			g.setFont(new Font("arial", Font.BOLD, 140));
			
			if (counter2 < 256) {
				g.setColor(new Color(255, 255, 255, counter2));
				g.drawString("WAVE " + wave, Game.WIDTH / 2 - 260, Game.HEIGHT / 2 - 25);
			} else if (counter2 > 255 && counter2 < 512) {
				g.setColor(new Color(255, 255, 255, 255 - counter2 % 256));
				g.drawString("WAVE " + wave, Game.WIDTH / 2 - 260, Game.HEIGHT / 2 - 25);
			} else {
				spawn.spawnWave(wave - 1);
				state = GameState.Game;
			}
			
			
			
		}

		// RENDERING WHEN PLAYING GAME

		else if (state == GameState.Game) {

			g2d.translate(-camera.getX(), -camera.getY());

			g.drawImage(lvlImg, 0, 0, null);
			handler.render(g);
			player.render(g);

			g2d.translate(camera.getX(), camera.getY());

			hud.render(g);
			miniMap.render(g);

			g.setColor(Color.BLACK);

			g.setFont(new Font("arial", Font.BOLD, 12));
			g.drawString("[" + player.x + ", " + player.y + "]", 13, 105);

			if (counter2 >= 0 && counter2 <= 255) {
				g.setColor(new Color(0, 0, 0, 255 - counter2));
				g.fillRect(0, 0, WIDTH, HEIGHT);
			}
		}

		if(state == GameState.Instructions) {
			g2d.translate(-camera.getX(), -camera.getY());
			
			g.drawImage(lvlImg, 0, 0, null);
			g2d.translate(camera.getX(), camera.getY());
			
			g.drawImage(instructionScreen,0,0,null);
			
			menu.render(g);
		}
		
		if(state == GameState.Options) {
			g2d.translate(-camera.getX(), -camera.getY());
			
			g.drawImage(lvlImg, 0, 0, null);
			g2d.translate(camera.getX(), camera.getY());
			
			menu.render(g);
			
		}
		
		
		// Rendering when paused
		if (state == GameState.Pause) {

			g2d.translate(-camera.getX(), -camera.getY());

			g.drawImage(lvlImg, 0, 0, null);
			handler.render(g);
			player.render(g);

			g2d.translate(camera.getX(), camera.getY());

			menu.render(g);

		}

		// Rendering when Game is over
		if (state == GameState.GameOver) {
			
			g2d.translate(-camera.getX(), -camera.getY());
			g.drawImage(lvlImg, 0, 0, null);
			g2d.translate(camera.getX(), camera.getY());
			
			menu.render(g);
		}
		
		// showing graphics
		g.dispose();
		bs.show();
	}
	
	public void resetGame(boolean menu) {
		coins = 0;
		wave = 1;
		enemiesKilled = 0;
		spawn.resetWave();
		
		player = new Player(MapDim.width / 2 - 16, MapDim.height / 2 - 16, this);
		hud = new HUD(this);
		handler = new EntityHandler(this, player);
		
		if (menu) {
			state = GameState.Menu;
		} else {
			counter2 = 0;
			state = GameState.WaveTransition;
		}
	}

	// Could be used to animate in
	public void animateIn(Graphics g) {
		if (counter2 >= 0 && counter2 <= 255) {
			g.setColor(new Color(0, 0, 0, 255 - counter2));
			g.fillRect(0, 0, WIDTH, HEIGHT);
		}
	}

	
	// For variables that need to be bound to a certain value(s)
	public static float clamp(int var, int min, int max) {
		if (var >= max)
			return var = max;
		else if (var <= min)
			return var = min;
		else
			return var;
	}

	
	// Get a user name for score at the end
	public String getUsername() {
		String username = JOptionPane.showInputDialog("Enter your name: ");
		if (username == null)
			username = "unknown";
		return username;
	}

	// Create instance of Game
	public static void main(String[] args) {
		new Game();
	}
}
