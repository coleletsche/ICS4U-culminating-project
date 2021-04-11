package game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import audio.MusicPlayer;
import graphics.Sprite;

public class Menu extends MouseAdapter implements MouseMotionListener {

	private boolean mute = true;

	
	// Init buttons
	private Button back;
	private Button backOptions;
	private Button musicOn;
	private Button musicOff;
	private Button b1;
	private Button b2;
	private Button b3;
	private Button b4;
	private Button b5;
	private Button b6;
	private Button b7;
	private Button mainMenuGO;
	private Button playAgain;

	private BufferedImage title = Sprite.title.getImage();
	private BufferedImage enemiesKilled = Sprite.enemiesKilled.getImage();
	private BufferedImage wave = Sprite.wave.getImage();
	private BufferedImage coinImg = Sprite.coins[0].getImage();

	private Game game;
	
	private int buttonOpacity = 128;
	private float titleCounter = 0;
	private Color buttonColour = new Color(0, 0, 0, buttonOpacity);

	
	// ArrayList of buttons
	private ArrayList<Button> menu_buttons = new ArrayList<Button>();
	private ArrayList<Button> pause_buttons = new ArrayList<Button>();
	private ArrayList<Button> instructions_buttons = new ArrayList<Button>();
	private ArrayList<Button> option_buttons = new ArrayList<Button>();
	private ArrayList<Button> gameOver_buttons = new ArrayList<Button>();

	public Menu(Game game) {
		this.game = game;

		// Setting up buttons
		back = new Button(Game.WIDTH / 2 - 150, Game.HEIGHT - 100, 300, 40, "BACK", "arial", 25, buttonColour);
		backOptions = new Button(Game.WIDTH / 2 - 150, Game.HEIGHT - 100, 300, 40, "BACK", "arial", 25, buttonColour);
		musicOn = new Button(Game.WIDTH / 2, 225, 300, 40, "MUSIC ON", "arial", 25, buttonColour);
		musicOff = new Button(Game.WIDTH / 2 - 300, 225, 300, 40, "MUSIC OFF", "arial", 25, buttonColour);

		b1 = new Button(Game.WIDTH / 2 - 300, 225, 600, 40, "PLAY", "arial", 25, buttonColour);
		b2 = new Button(Game.WIDTH / 2 - 300, 280, 600, 40, "INSTRUCTIONS", "arial", 25, buttonColour);
		b3 = new Button(Game.WIDTH / 2 - 300, 335, 295, 40, "OPTIONS", "arial", 25, buttonColour);
		b4 = new Button(Game.WIDTH / 2 + 5, 335, 295, 40, "QUIT", "arial", 25, buttonColour);

		b5 = new Button(Game.WIDTH / 2 - 300, 225, 600, 40, "RESUME", "arial", 25, buttonColour);
		b6 = new Button(Game.WIDTH / 2 - 300, 280, 600, 40, "MAIN MENU", "arial", 25, buttonColour);
		b7 = new Button(Game.WIDTH / 2 - 300, 355, 600, 40, "QUIT", "arial", 25, buttonColour);
		
		mainMenuGO = new Button(Game.WIDTH / 2 - 305, 450, 300, 40, "MAIN MENU", "arial", 25, buttonColour);
		playAgain = new Button(Game.WIDTH / 2 + 5, 450, 300, 40, "PLAY AGAIN", "arial", 25, buttonColour);

		// Add Menu Buttons
		menu_buttons.add(b1);
		menu_buttons.add(b2);
		menu_buttons.add(b3);
		menu_buttons.add(b4);

		// Add Pause Buttons
		pause_buttons.add(b5);
		pause_buttons.add(b6);
		pause_buttons.add(b7);

		// Instruction Buttons
		instructions_buttons.add(back);
		// Option Buttons
		option_buttons.add(musicOn);
		option_buttons.add(musicOff);
		option_buttons.add(backOptions);
		// Game over buttons
		gameOver_buttons.add(mainMenuGO);
		gameOver_buttons.add(playAgain);		
	}

	public void mousePressed(MouseEvent e) {
		int mx = e.getX();
		int my = e.getY();

		// Mouse events based of GameState
		if (game.state == Game.GameState.Menu) {

			if (b1.intersectsAtPoint(mx, my)) {
				game.state = Game.GameState.AnimateIn;
				game.bgMusic.close();
				if (mute) {
					game.bgMusic = new MusicPlayer("/Music/level.wav");
					game.bgMusic.play();
				}
			}

			if (b2.intersectsAtPoint(mx, my)) {
				game.state = Game.GameState.Instructions;
			}
			if (b3.intersectsAtPoint(mx, my)) {
				game.state = Game.GameState.Options;
			}

			if (b4.intersectsAtPoint(mx, my))
				System.exit(0);
		} else if (game.state == Game.GameState.Pause) {
			if (b5.intersectsAtPoint(mx, my)) {
				game.state = Game.GameState.Game;
			}

			if (b6.intersectsAtPoint(mx, my)) {
				game.resetGame(true);
				game.state = Game.GameState.Menu;
			}

			if (b7.intersectsAtPoint(mx, my)) {
				System.exit(0);
			}
		} else if (game.state == Game.GameState.Instructions) {
			if (back.intersectsAtPoint(mx, my)) {
				game.state = Game.GameState.Menu;
			}
		} else if (game.state == Game.GameState.Options) {
			if (back.intersectsAtPoint(mx, my)) {
				game.state = Game.GameState.Menu;
			}
			if (musicOn.intersectsAtPoint(mx, my)) {
				game.bgMusic.setVol(0);
				mute = true;
			}
			if (musicOff.intersectsAtPoint(mx, my)) {
				game.bgMusic.setVol(-80);
				mute = false;
			}
		} else if (game.state == Game.GameState.GameOver) {
			if (mainMenuGO.intersectsAtPoint(mx, my)) {
				game.resetGame(true);
			}
			if (playAgain.intersectsAtPoint(mx, my)) {
				// Make sure game is reset
				game.resetGame(false);
			}
		}

	}

	public void mouseMoved(MouseEvent e) {
		int mx = e.getX();
		int my = e.getY();

		
		// Changing the colour of the buttons in different state
		if (game.state == Game.GameState.Menu) {

			for (Button b : menu_buttons) {
				if (b.intersectsAtPoint(mx, my))
					b.setButtonColour(new Color(0, 0, 0, 255));
				else
					b.setButtonColour(buttonColour);
			}
		} else if (game.state == Game.GameState.Pause) {

			for (Button b : pause_buttons) {
				if (b.intersectsAtPoint(mx, my))
					b.setButtonColour(new Color(0, 0, 0, 255));
				else
					b.setButtonColour(buttonColour);
			}
		} else if (game.state == Game.GameState.Instructions) {
			for (Button b : instructions_buttons) {
				if (b.intersectsAtPoint(mx, my))
					b.setButtonColour(new Color(0, 0, 0, 255));
				else
					b.setButtonColour(buttonColour);
			}
		} else if (game.state == Game.GameState.Options) {
			for (Button b : option_buttons) {
				if (b.intersectsAtPoint(mx, my))
					b.setButtonColour(new Color(0, 0, 0, 255));
				else
					b.setButtonColour(buttonColour);
			}
		} else if (game.state == Game.GameState.GameOver) {
			for (Button b : gameOver_buttons) {
				if (b.intersectsAtPoint(mx, my))
					b.setButtonColour(new Color(0, 0, 0, 255));
				else
					b.setButtonColour(buttonColour);
			}
		}

	}

	public void tick() {
		titleCounter += .10f;
	}

	public void render(Graphics g) {
		// Make sure that the outline fillrect has same opacity and foreground
		if (game.state == Game.GameState.Menu) {

			
			// Drawing the title
			g.drawImage(title, Game.WIDTH / 2 - title.getWidth() / 2, (int) (70 + 10 * Math.sin(titleCounter)), null);

			
			// Rendering the menu buttons
			b1.render(g, 2, Color.WHITE);
			b2.render(g, 2, Color.WHITE);
			b3.render(g, 2, Color.WHITE);
			b4.render(g, 2, Color.WHITE);
		} else if (game.state == Game.GameState.Pause) {

			g.setColor(Color.WHITE);
			g.setFont(new Font("arial", Font.BOLD, 70));
			g.drawString("PAUSED!", Game.WIDTH / 2 - 150, 150);

			
			// Rendering the pause buttons
			b5.render(g, 2, Color.WHITE);
			b6.render(g, 2, Color.WHITE);
			b7.render(g, 2, Color.WHITE);
		} else if (game.state == Game.GameState.Instructions) {
			// Rendering the Instructions buttons
			back.render(g, 2, Color.WHITE);
		} else if (game.state == Game.GameState.Options) {
			// Rendering the Options buttons
			backOptions.render(g, 2, Color.WHITE);
			musicOn.render(g, 2, Color.WHITE);
			musicOff.render(g, 2, Color.WHITE);
		} else if (game.state == Game.GameState.GameOver) {
			
			// Rendering GAME OVER!
			g.setColor(Color.WHITE);
			g.setFont(new Font("arial", Font.BOLD, 130));
			g.drawString("GAME OVER!", Game.WIDTH / 2 - 400, 150);
			
			g.setColor(Color.WHITE);
			g.fillRect(Game.WIDTH / 2 - 250, 200, 500, 200);
			
			g.setColor(Color.BLACK);
			g.fillRect(Game.WIDTH / 2 - 245, 205, 490, 190);
			
			g.setColor(Color.WHITE);
			g.drawLine(Game.WIDTH / 2, 200, Game.WIDTH / 2, 400);
			
			g.setFont(new Font("arial", Font.BOLD, 25));
			g.drawString("Your Statistics:", Game.WIDTH / 2 - 210, 235);
			g.drawString("High Scores:", Game.WIDTH / 2 + 50, 235);
			
			g.drawImage(coinImg, Game.WIDTH / 2 - 225, 260, null);
			g.drawImage(coinImg, Game.WIDTH / 2 + 20, 260, null);
			
			g.drawImage(wave, Game.WIDTH / 2 - 235, 300, null);
			g.drawImage(wave, Game.WIDTH / 2 + 15, 300, null);
			
			g.drawImage(enemiesKilled, Game.WIDTH / 2 - 240, 345, null);
			g.drawImage(enemiesKilled, Game.WIDTH / 2 + 10, 345, null);
			
			g.setFont(new Font("arial", Font.BOLD, 20));
			g.drawString("=        " + game.coins, Game.WIDTH / 2 - 170, 275);
			g.drawString("=        " + game.wave, Game.WIDTH / 2 - 170, 325);
			g.drawString("=        " + game.enemiesKilled, Game.WIDTH / 2 - 170, 375);
			

			g.drawString("=        " + game.highScore, Game.WIDTH / 2 + 80, 275);
			g.drawString("=        " + game.highestWave, Game.WIDTH / 2 + 80, 325);
			g.drawString("=        " + game.highestEnemiesKilled, Game.WIDTH / 2 + 80, 375);
			
			// Rendering the Game Over buttons
			mainMenuGO.render(g, 2,Color.WHITE);
			playAgain.render(g, 2, Color.WHITE);
		}
	}
}
