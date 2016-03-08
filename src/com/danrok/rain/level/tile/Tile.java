package com.danrok.rain.level.tile;

import com.danrok.rain.graphics.Screen;
import com.danrok.rain.graphics.Sprite;
import com.danrok.rain.level.tile.spawn_level.SpawnFloorTile;
import com.danrok.rain.level.tile.spawn_level.SpawnGrassTile;
import com.danrok.rain.level.tile.spawn_level.SpawnHedgeTile;
import com.danrok.rain.level.tile.spawn_level.SpawnWallTile;
import com.danrok.rain.level.tile.spawn_level.SpawnWaterTile;

public class Tile {

	public Sprite sprite;
	
	public static Tile grass = new GrassTile(Sprite.grass);
	public static Tile flower = new FlowerTile(Sprite.flower);
	public static Tile rock = new RockTile(Sprite.rock);
	public static Tile voidTile = new VoidTile(Sprite.voidSprite);
	
	public static Tile spawn_grass = new SpawnGrassTile(Sprite.spawn_grass);
	public static Tile spawn_hedge = new SpawnHedgeTile(Sprite.spawn_hedge);
	public static Tile spawn_water = new SpawnWaterTile(Sprite.spawn_water);
	public static Tile spawn_wall1 = new SpawnWallTile(Sprite.spawn_wall1);
	public static Tile spawn_wall2 = new SpawnWallTile(Sprite.spawn_wall2);
	public static Tile spawn_floor = new SpawnFloorTile(Sprite.spawn_floor);
	
	public static final int col_spawn_grass = 0xFF00FF00;
	public static final int col_spawn_hedge = 0xFF008700;
	public static final int col_spawn_water = 0xFF00889c;
	public static final int col_spawn_wall1 = 0xFF8a8a8a;
	public static final int col_spawn_wall2 = 0xFF555555;
	public static final int col_spawn_floor = 0xFF6e4d00;
	
	public Tile(Sprite sprite){
		this.sprite = sprite;
	}
	
	public void render(int x, int y, Screen screen){
	}
	
	public boolean isSolid() {
		return false;
	}

}
