package entity;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Iterator;

import game.Game;

public class EntityHandler {

	/* this class handles all the entitys (collisions, updates, renders) */

	public ArrayList<Entity> obj = new ArrayList<Entity>();

	private Game game;

	private Player player;

	private int range = 50;

	ArrayList<Entity> remove = new ArrayList<Entity>();
	ArrayList<Entity> add = new ArrayList<Entity>();

	public EntityHandler(Game game, Player player) {
		this.game = game;
		this.player = player;
	}

	public void tick() {

		// Won't throw ConcurrentModificationException
		remove = new ArrayList<Entity>();
		add = new ArrayList<Entity>();

		// Go through the entities
		for (Entity entity : obj) {
			entity.tick();

			// Coins
			if (entity instanceof Coin) {
				if (entity.getBounds().intersects(player.getBounds())) {
					remove.add(entity);
					game.coins += 1;
				}
			}
			// MedKit
			else if (entity instanceof MedKit) {
				if (entity.getBounds().intersects(player.getBounds())) {
					remove.add(entity);
					player.addHealth(MedKit.HEALTH_INCREASE);
				}
			}

			// All instances of the Mob class
			else if (entity instanceof Mob) {
				// If the mob is dead
				if (((Mob) entity).health <= 0) {
					remove.add(entity);
					game.enemiesKilled++;
					int coins = 0;
					float chanceToSpawnMedKit = 0;

					// Change vars based on the instance of the mob

					if (entity instanceof SlimeEnemy) {
						coins = game.r.nextInt(SlimeEnemy.coinsDrop) + 1;
						chanceToSpawnMedKit = SlimeEnemy.chanceToSpawnMedKit;
					} else if (entity instanceof MedicEnemy) {
						coins = game.r.nextInt(MedicEnemy.coinsDrop) + 1;
						chanceToSpawnMedKit = MedicEnemy.chanceToSpawnMedKit;
					} else if (entity instanceof FastEnemy) {
						coins = game.r.nextInt(FastEnemy.coinsDrop) + 1;
						chanceToSpawnMedKit = FastEnemy.chanceToSpawnMedKit;
					} else if (entity instanceof TankyEnemy) {
						coins = game.r.nextInt(TankyEnemy.coinsDrop) + 1;
						chanceToSpawnMedKit = TankyEnemy.chanceToSpawnMedKit;
					}
					// Spawn the correct amount of coins
					for (int i = 0; i < coins; i++)
						add.add(new Coin((int) entity.x + game.r.nextInt(range) - range / 2,
								(int) entity.y + game.r.nextInt(range) - range / 2, game));

					// Spawn a medkit if lucky
					if (game.r.nextFloat() * 100 <= chanceToSpawnMedKit)
						add.add(new MedKit((int) entity.x + 16, (int) entity.y + 16, game));
				}

				// MedicEnemy
				if (((Mob) entity) instanceof MedicEnemy) {
					for (Entity targ : obj) {
						if (targ instanceof Mob) {
							if (((MedicEnemy) entity).getHealingBounds().intersects(targ.getBounds())) {
								((Mob) targ).health += MedicEnemy.healthInc;
							}
						}
					}
				}

				// This is where the enemy does damage

				if (entity.getBounds().intersects(player.getBounds())) {
					player.damagePlayer(((Mob) entity).damage);
				}

			}

			// Projectile
			else if (entity instanceof Projectile) {
				if (entity.x < -10 || entity.x > Game.MapDim.width + 10 || entity.y < -10
						|| entity.y > Game.MapDim.height + 10) {
					remove.add(entity);
				}
				// Checking if the projectile hit the player;
				for (Entity enemy : obj) {
					if (enemy instanceof Mob) {
						if (entity.getBounds().intersects(enemy.getBounds())) {
							((Mob) enemy).health -= ((Projectile) entity).damage;
							remove.add(entity);
							break;
						}
					}
					if (enemy instanceof ExplosiveContainer) {
						if (entity.getBounds().intersects(enemy.getBounds())) {
							remove.add(entity);
							remove.add(enemy);
							for (Entity enemys : obj) {
								if (enemys instanceof Mob) {
									int dist = (int) Math
											.sqrt(Math.pow(enemys.x - enemy.x, 2) + Math.pow(enemys.y - enemy.y, 2));
									System.out.println(dist);
									if (dist <= ExplosiveContainer.explosiveRange) {
										((Mob) enemys).health -= (((ExplosiveContainer) enemy).explosiveDamage);
									}
								}
							}
						}
					}
				}
			}
		}
		obj.removeAll(remove);
		obj.addAll(add);
	}

	// Render all the entities
	public void render(Graphics g) {

		Iterator<Entity> iter = obj.iterator();

		while (iter.hasNext()) {
			iter.next().render(g);
		}
	}

	public int getNumberOfEnemies() {
		int num = 0;
		for (Entity e : obj)
			if (e instanceof Mob)
				num++;
		return num;
	}

	public void add(Entity entity) {
		this.obj.add(entity);
	}

	public void remove(Entity entity) {
		this.obj.remove(entity);
	}

	public void removeAll() {
		this.obj.clear();
	}
}
