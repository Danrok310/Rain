package com.danrok.rain.entity.mob;

import java.util.List;

import com.danrok.rain.entity.projectile.Projectile;
import com.danrok.rain.entity.projectile.ShooterProjectile;
import com.danrok.rain.graphics.AnimatedSprite;
import com.danrok.rain.graphics.Screen;
import com.danrok.rain.graphics.Sprite;
import com.danrok.rain.graphics.SpriteSheet;

public class Shooter extends Mob {

	private AnimatedSprite down = new AnimatedSprite(SpriteSheet.shooter_down, 32, 32, 3);
	private AnimatedSprite up = new AnimatedSprite(SpriteSheet.shooter_up, 32, 32, 3);
	private AnimatedSprite left = new AnimatedSprite(SpriteSheet.shooter_left, 32, 32, 3);
	private AnimatedSprite right = new AnimatedSprite(SpriteSheet.shooter_right, 32, 32, 3);
	private double xa = 0;
	private double ya = 0;
	private double speed = 2.0;
	private int time = 0;

	private AnimatedSprite animSprite = down;

	Projectile p;
	private int fireRate = 0;

	public Shooter(int x, int y) {
		this.x = x << 4;
		this.y = y << 4;
		sprite = Sprite.shooter;
	}

	protected void shoot(double x, double y, double dir) {
		Projectile p = new ShooterProjectile(x, y, dir);
		level.add(p);

	}

	public void update() {
		time++;
		if (time % (random.nextInt(50) + 30) == 0) {
			xa = (random.nextInt(3) - 1) * speed;
			ya = (random.nextInt(3) - 1) * speed;
			if (random.nextInt(4) == 0) {
				xa = 0;
				ya = 0;
			}
		}
		if (fireRate > 0) fireRate--;
		if (walking) animSprite.update();
		else
			animSprite.setFrame(1);
		if (ya < 0) {
			animSprite = up;
			dir = Direction.UP;
		} else if (ya > 0) {
			animSprite = down;
			dir = Direction.DOWN;
		}
		if (xa < 0) {
			animSprite = left;
			dir = Direction.LEFT;
		} else if (xa > 0) {
			animSprite = right;
			dir = Direction.RIGHT;
		}
		if (xa != 0 || ya != 0) {
			move(xa, ya);
			walking = true;
		} else {
			walking = false;
		}
		clear();
		updateShooting();

	}

	private void clear() {
		for (int i = 0; i < level.getProjectiles().size(); i++) {
			Projectile p = level.getProjectiles().get(i);
			if (p.isRemoved()) level.getProjectiles().remove(i);
		}
	}

	private void updateShooting() {List<Player> players = level.getPlayers(this, 100);
		if (players.size() > 0 && fireRate <= 0) {
			Player p = level.getClientPlayer();
			double dx = p.getX() - x;
			double dy = p.getY() - y;
			double dir = Math.atan2(dy, dx);
			shoot(x, y, dir);
			fireRate = ShooterProjectile.FIRE_RATE;
		} 
	}

	public void render(Screen screen) {
		sprite = animSprite.getSprite();
		screen.renderMob((int) (x - 16), (int) (y - 16), this);
	}

}
