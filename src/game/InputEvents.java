package game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import entity.Bullet;
import entity.Player;
import game.Game.GameState;

public class InputEvents implements KeyListener, MouseListener {

	private Game game;

	public float speed = 2;

	public int counter = 0;
	public int lastCount = 0;

	public int[] keys = { KeyEvent.VK_D, KeyEvent.VK_A, KeyEvent.VK_S, KeyEvent.VK_W, KeyEvent.VK_SHIFT };
	public boolean[] keyDown = new boolean[keys.length];

	public InputEvents(Game game) {
		this.game = game;
	}

	public void tick() {
		if (counter > Integer.MAX_VALUE)
			counter = 0;
		counter++;
	}

	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		if (game.state == GameState.Game) {

			for (int i = 0; i < keys.length; i++)
				if (key == keys[i])
					keyDown[i] = true;

			if (key == KeyEvent.VK_ESCAPE) {
				game.state = GameState.Pause;
			}
		} else if (game.state == GameState.WaveTransition) {
			for (int i = 0; i < keys.length; i++)
				if (key == keys[i])
					keyDown[i] = true;
		} else if (game.state == Game.GameState.Pause) {
			if (key == KeyEvent.VK_ESCAPE) {
				game.state = GameState.Game;
			}
		}
	}

	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();

		for (int i = 0; i < keys.length; i++)
			if (key == keys[i])
				keyDown[i] = false;
	}

	public void mousePressed(MouseEvent e) {
		int mx = e.getX() + game.camera.getX();
		int my = e.getY() + game.camera.getY();

		if (game.state == GameState.Game) {
			if (counter - lastCount >= 10) {
				game.handler.add(new Bullet((int) game.player.x + Player.playerDim.width / 2,
						(int) game.player.y + Player.playerDim.height / 2, 2.5f, mx, my, game));
				lastCount = counter;
			}
		}
	}

	// Unused but must be in the class because it implements the Mouse/Key event
	// listeners
	public void mouseClicked(MouseEvent e) {
	}

	public void mouseReleased(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void keyTyped(KeyEvent e) {
	}
}
