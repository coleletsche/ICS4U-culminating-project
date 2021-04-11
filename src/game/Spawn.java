package game;

import java.util.Random;

import entity.ExplosiveContainer;
import entity.FastEnemy;
import entity.MedicEnemy;
import entity.SlimeEnemy;
import entity.TankyEnemy;

public class Spawn {

	private Game game;

	private Random r = new Random();

	// Damage for each enemy
	private static final float SlimeDamage = 0.5f;
	private static final float MedicDamage = 0.1f;
	private static final float FastEnemyDamage = 1f;
	private static final float TankyDamage = 1.5f;

	
	private double[] wavesInitial = {2, 0, 0, 0};
	private double[] waves = wavesInitial.clone();

	public Spawn(Game game) {
		this.game = game;
	}

	public void spawnWave(int wave) {
		waves[0] +=  wave * 0.5;
		waves[1] += wave / 10;
		waves[2] = wave;
		waves[3] += wave / 5;
		
		for (int i = 0; i < 5; i++) {
			game.handler.add(new ExplosiveContainer(100 + 100 * i, 100, game));
		}
		
		// Add Enemies 
		for (int i = 0; i < (int) waves[0]; i++)
			game.handler.add(new SlimeEnemy(r.nextInt(Game.MapDim.width - 64) + 32, r.nextInt(Game.MapDim.height - 64) + 32,
					25.0f, 25.0f, SlimeDamage, game));
		
		for (int i = 0; i < (int) waves[1]; i++)
			game.handler.add(new MedicEnemy(r.nextInt(Game.MapDim.width - 64) + 32, r.nextInt(Game.MapDim.height - 64) + 32,
					5.0f, 5.0f, MedicDamage, game));
		
		for (int i = 0; i < (int) waves[2]; i++)
			game.handler.add(new FastEnemy(r.nextInt(Game.MapDim.width - 64) + 32, r.nextInt(Game.MapDim.height - 64) + 32,
					10.0f, 10.0f, FastEnemyDamage, game));
		
		for (int i = 0; i < (int) waves[3]; i++)
			game.handler.add(new TankyEnemy(r.nextInt(Game.MapDim.width - 64) + 32, r.nextInt(Game.MapDim.height - 64) + 32,
					75.0f, 75.0f, TankyDamage, game));
	}
	
	public void resetWave() {
		this.waves = wavesInitial.clone();
	}
}
