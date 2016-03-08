package com.danrok.rain.entity.mob;

import com.danrok.rain.entity.Entity;
import com.danrok.rain.entity.projectile.Projectile;
import com.danrok.rain.entity.projectile.SorcererProjectile;
import com.danrok.rain.graphics.Screen;

public abstract class Mob extends Entity {

	public int rightXOffset = 12;
	public int leftXOffset = -16;
	public int topYOffset = -12;
	public int bottomYOffset = 16;

	protected boolean moving = false;
	protected boolean walking = false;

	protected int health;
	protected int mana;

	protected enum Direction {
		UP, DOWN, LEFT, RIGHT
	}

	protected Direction dir;

	public void move(double xa, double ya) {
		if (xa != 0 && ya != 0) {
			move(xa, 0);
			move(0, ya);
			return;
		}
		if (xa > 0) dir = Direction.RIGHT;
		if (xa < 0) dir = Direction.LEFT;
		if (ya > 0) dir = Direction.DOWN;
		if (ya < 0) dir = Direction.UP;
		while (xa != 0) {
			if (Math.abs(xa) > 1) {
				if (!isCollision(abs(xa), leftXOffset, rightXOffset, ya, topYOffset, bottomYOffset)) {
					this.x += abs(xa);
				}
				xa -= abs(xa);
			} else {
				if (!isCollision(abs(xa), leftXOffset, rightXOffset, ya, topYOffset, bottomYOffset)) {
					this.x += xa;
				}
				xa = 0;
			}
		}
		while (ya != 0) {
			if (Math.abs(ya) > 1) {
				if (!isCollision(xa, leftXOffset, rightXOffset, abs(ya), topYOffset, bottomYOffset)) {
					this.y += abs(ya);
				}
				ya -= abs(ya);
			} else {
				if (!isCollision(xa, leftXOffset, rightXOffset, abs(ya), topYOffset, bottomYOffset)) {
					this.y += ya;
				}
				ya = 0;
			}
		}
	}

	private int abs(double value) {
		if (value < 0) return -1;
		return 1;
	}

	public abstract void update();

	public abstract void render(Screen screen);

	protected void shoot(double x, double y, double dir) {
		Projectile p = new SorcererProjectile(x, y, dir);
		level.add(p);

	}

	/* private boolean collision(double xa, double ya) {
		boolean solid = false;
		for (int c = 0; c < 4; c++) {
			double xt = ((x + xa) - c % 2 * 15) / 16;
			double yt = ((y + ya) - c / 2 * 15) / 16;
			int ix = (int) Math.ceil(xt);
			int iy = (int) Math.ceil(yt);
			if (c % 2 == 0) ix = (int) Math.floor(xt);
			if (c / 2 == 0) iy = (int) Math.floor(yt);
			if (level.getTile(ix, iy).isSolid()) solid = true;
		}
		return solid;
	}*/

}
