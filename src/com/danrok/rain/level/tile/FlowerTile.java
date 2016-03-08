package com.danrok.rain.level.tile;

import com.danrok.rain.graphics.Screen;
import com.danrok.rain.graphics.Sprite;

public class FlowerTile extends Tile {

	public FlowerTile(Sprite sprite) {
		super(sprite);
	}
	
	public void render(int x, int y, Screen screen){
		screen.renderTile(x << 4,  y << 4, this);
	}

}
