package com.danrok.rain.entity;

import java.util.Random;

import com.danrok.rain.graphics.Screen;
import com.danrok.rain.graphics.Sprite;
import com.danrok.rain.level.Level;

public class Entity {

	protected double x, y;
	protected Sprite sprite;
	private boolean removed = false;
	protected Level level;
	protected final Random random = new Random();
	public int health;

	public Entity() {

	}

	public Entity(int x, int y, Sprite sprite) {
		this.x = x;
		this.y = y;
	}

	public void update() {
	}

	public void render(Screen screen) {
		if (sprite != null) screen.renderSprite((int)x, (int)y, sprite, true);
	}

	public void remove() {
		//Remove from level
		removed = true;
	}
	
	public double getX(){
		return x;
	}
	
	public double getY(){
		return y;
	}

	public Sprite getSprite() {
		return sprite;
	}

	public boolean isRemoved() {
		return removed;
	}

	public void init(Level level) {
		this.level = level;
	}
	
	protected boolean isCollision(double xa, int leftXOffset, int rightXOffset, double ya, int topYOffset, int bottomYOffset) {
	      boolean solid = false;
	      int xt0 = hitboxX(xa, leftXOffset);
	      int xt1 = hitboxX(xa, rightXOffset);
	      int yt0 = hitboxY(ya, topYOffset);
	      int yt1 = hitboxY(ya, bottomYOffset);
	      for (int c = 0; c < 4; c++) {
	         if (level.getTile(xt0, yt0).isSolid() || level.getTile(xt1, yt1).isSolid() || level.getTile(xt0, yt1).isSolid() || level.getTile(xt1, yt0).isSolid()){
	            solid = true;
	         }
	      }
	      return solid;
	   }

	   protected int hitboxX(double xa, int width){
	      int hor = 0;
	      for(int c = 0; c < 4; c++){
	         double xt = ((x + xa) - c % 2 * 15 + width) / 16;
	         hor = (int) Math.ceil(xt);
	         if(c % 2 == 0){
	            hor = (int) Math.floor(xt);
	         }
	      }
	      return hor;
	   }


	   protected int hitboxY(double ya, int height){
	      int vert = 0;
	      for (int c = 0; c < 4; c++){
	         double yt = ((y + ya) - c / 2 * 15 + height) / 16;
	         vert = (int) Math.ceil(yt);
	         if (c / 2 == 0) {
	            vert = (int) Math.floor(yt);
	         }
	      }
	      return vert;
	   } 

}
