package com.danrok.rain.level;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.danrok.rain.entity.mob.Chaser;
import com.danrok.rain.entity.mob.Dummy;
import com.danrok.rain.entity.mob.Shooter;
import com.danrok.rain.entity.mob.Star;

public class SpawnLevel extends Level {

	public SpawnLevel(String path) {
		super(path);
	}

	protected void loadLevel(String path) {
		try {
			BufferedImage image = ImageIO.read(SpawnLevel.class.getResource(path));
			int w = width = image.getWidth();
			int h = height = image.getHeight();
			tiles = new int[w * h];
			image.getRGB(0, 0, w, h, tiles, 0, w);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Exception! Could not load level file!");
		}
		add(new Chaser(20, 22));
		add(new Star(17, 20));
		for (int i = 0; i < 4; i++) {
			add(new Dummy(20, 22));
		}
		for (int i = 0; i < 2; i++) {
			add(new Shooter(19, 20));
		}
	}

	// Grass = 0x00FF00
	// Flower = 0xFFFF00
	// Rock = 0x7F7F00
	protected void generateLevel() {

	}
}
