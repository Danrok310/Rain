package com.danrok.rain.level.tile.spawn_level;

import com.danrok.rain.graphics.Screen;
import com.danrok.rain.graphics.Sprite;
import com.danrok.rain.level.tile.Tile;

public class SpawnWallTile extends Tile {

	public SpawnWallTile(Sprite sprite) {
		super(sprite);
		// TODO Auto-generated constructor stub
	}
	
	public void render(int x, int y, Screen screen){
		screen.renderTile(x << 4,  y << 4, this);
	}
	
	public boolean isSolid(){
		return true;
	}

}
