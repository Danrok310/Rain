package com.danrok.rain.entity.projectile;

import com.danrok.rain.entity.spawner.ParticleSpawner;
import com.danrok.rain.graphics.Screen;
import com.danrok.rain.graphics.Sprite;

public class ShooterProjectile extends Projectile {

	public static final int FIRE_RATE = 10;

	public ShooterProjectile(double x, double y, double dir) {
		super(x, y, dir);
		range = 150;
		speed = 4;
		damage = 20;
		sprite = Sprite.projectile_shooter;
		nx = speed * Math.cos(angle);
		ny = speed * Math.sin(angle);
	}

	public void update() {
		if (level.tileCollision((int)(x + nx), (int)(y + ny), 14, 5, 5)) {
			level.add(new ParticleSpawner((int)x, (int)y, 44, 50, level));
			remove();
		}
		move();
	}

	protected void move() {
		x += nx;
		y += ny;
		if (distance() > range) remove();
	}

	private double distance() {
		double dist = 0;
		dist = Math.sqrt(Math.abs((xOrigin - x) * (xOrigin - x) + (yOrigin - y) * (yOrigin - y)));
		return dist;

	}

	public void render(Screen screen) {
		screen.renderProjectile((int) x - 12, (int) y - 2, this);
	}


}
