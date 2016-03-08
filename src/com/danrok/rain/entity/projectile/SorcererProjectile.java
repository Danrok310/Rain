package com.danrok.rain.entity.projectile;

import java.util.List;

import com.danrok.rain.entity.mob.Mob;
import com.danrok.rain.entity.spawner.ParticleSpawner;
import com.danrok.rain.graphics.Screen;
import com.danrok.rain.graphics.Sprite;
import com.danrok.rain.level.Level;

public class SorcererProjectile extends Projectile {

	public static final int FIRE_RATE = 10;

	public SorcererProjectile(double x, double y, double dir) {
		super(x, y, dir);
		range = 150;
		speed = 4;
		damage = 20;
		sprite = Sprite.rotate(Sprite.projectile_ranger, angle);
		nx = speed * Math.cos(angle);
		ny = speed * Math.sin(angle);
	}

	//private int time = 0;

	public void update() {
		if (level.tileCollision((int) (x + nx), (int) (y + ny), 14, 5, 5)) {
			level.add(new ParticleSpawner((int) x, (int) y, 44, 50, level));
			remove();
		}
		//time++; // Custom sprite rotation
		//if(time % 6 == 0){
		//	sprite = Sprite.rotate(sprite, Math.PI / 20); // Spinning sprite
		//}

		//Sprite.rotate(Sprite.projectile_ranger, 90);
		move();

		for (int i = 0; i < Level.entities.size(); i++) {

			if (x < Level.entities.get(i).getX() + 15
					&& x > Level.entities.get(i).getX() - 15// creates a 32x32 boundary
					&& y < Level.entities.get(i).getY() + 15
					&& y > Level.entities.get(i).getY() - 15
					) {
				remove();
				Level.entities.get(i).health -= 1; //need to track entity health

			}
		}
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
