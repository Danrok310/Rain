package com.danrok.rain.graphics;

public class Sprite {

	public final int SIZE;
	private int x, y;
	private int width, height;
	public int[] pixels;
	protected SpriteSheet sheet;

	public static Sprite grass = new Sprite(16, 0, 0, SpriteSheet.tiles);
	public static Sprite flower = new Sprite(16, 1, 0, SpriteSheet.tiles);
	public static Sprite rock = new Sprite(16, 2, 0, SpriteSheet.tiles);
	public static Sprite voidSprite = new Sprite(16, 0xF097EA);

	// Spawn Level Sprites
	public static Sprite spawn_grass = new Sprite(16, 0, 0, SpriteSheet.spawn_level);
	public static Sprite spawn_hedge = new Sprite(16, 1, 0, SpriteSheet.spawn_level);
	public static Sprite spawn_water = new Sprite(16, 2, 0, SpriteSheet.spawn_level);
	public static Sprite spawn_wall1 = new Sprite(16, 0, 1, SpriteSheet.spawn_level);
	public static Sprite spawn_wall2 = new Sprite(16, 0, 2, SpriteSheet.spawn_level);
	public static Sprite spawn_floor = new Sprite(16, 1, 1, SpriteSheet.spawn_level);

	// Player Sprites
	public static Sprite life_full = new Sprite(16, 0, 0, SpriteSheet.life);
	public static Sprite life_half = new Sprite(16, 1, 0, SpriteSheet.life);
	public static Sprite life_empty = new Sprite(16, 2, 0, SpriteSheet.life);
	public static Sprite player_down = new Sprite(32, 0, 4, SpriteSheet.tiles);
	public static Sprite player_down1 = new Sprite(32, 1, 4, SpriteSheet.tiles);
	public static Sprite player_down2 = new Sprite(32, 2, 4, SpriteSheet.tiles);
	public static Sprite player_left = new Sprite(32, 0, 5, SpriteSheet.tiles);
	public static Sprite player_left1 = new Sprite(32, 1, 5, SpriteSheet.tiles);
	public static Sprite player_left2 = new Sprite(32, 2, 5, SpriteSheet.tiles);
	public static Sprite player_right = new Sprite(32, 0, 6, SpriteSheet.tiles);
	public static Sprite player_right1 = new Sprite(32, 1, 6, SpriteSheet.tiles);
	public static Sprite player_right2 = new Sprite(32, 2, 6, SpriteSheet.tiles);
	public static Sprite player_up = new Sprite(32, 0, 7, SpriteSheet.tiles);
	public static Sprite player_up1 = new Sprite(32, 1, 7, SpriteSheet.tiles);
	public static Sprite player_up2 = new Sprite(32, 2, 7, SpriteSheet.tiles);

	// Projectile sprites
	public static Sprite projectile_ranger = new Sprite(16, 0, 0, SpriteSheet.projectile_ranger);
	public static Sprite projectile_sorcerer = new Sprite(16, 0, 0, SpriteSheet.projectile_sorcerer);
	public static Sprite projectile_shooter = new Sprite(16, 0, 0, SpriteSheet.projectile_shooter);

	// Particles
	public static Sprite particle_normal = new Sprite(3, 0xf5f5f5);
	public static Sprite square = new Sprite(2, 0xFF0000);

	// Mobs
	public static Sprite dummy = new Sprite(32, 0, 0, SpriteSheet.dummy_down);
	public static Sprite shooter = new Sprite(32, 0, 0, SpriteSheet.shooter_down);

	protected Sprite(SpriteSheet sheet, int width, int height) {
		SIZE = (width == height) ? width : -1;
		this.width = width;
		this.height = height;
		this.sheet = sheet;
	}

	public Sprite(int size, int x, int y, SpriteSheet sheet) {
		SIZE = size;
		this.width = size;
		this.height = size;
		pixels = new int[SIZE * SIZE];
		this.x = x * size;
		this.y = y * size;
		this.sheet = sheet;
		load();
	}

	public Sprite(int width, int height, int color) {
		SIZE = -1;
		this.width = width;
		this.height = height;
		pixels = new int[width * height];
		setColor(color);
	}

	public Sprite(int size, int color) {
		SIZE = size;
		this.width = size;
		this.height = size;
		pixels = new int[SIZE * SIZE];
		setColor(color);
	}

	public Sprite(int[] pixels, int width, int height) {
		SIZE = (width == height) ? width : -1;
		this.width = width;
		this.height = height;
		this.pixels = new int[pixels.length];
		System.arraycopy(pixels, 0, this.pixels, 0, pixels.length);
		/*
			for (int i = 0; i < pixels.length; i++) {
				this.pixels[i] = pixels[i];
			}
		*/
	}
	
	public static Sprite rotate(Sprite sprite, double angle){
		return new Sprite(rotate(sprite.pixels, sprite.width, sprite.height, angle), sprite.width, sprite.height);
	}
	
	private static int[] rotate(int[] pixels, int width, int height, double angle) {
		int[] result = new int[width * height];
		double nx_x = rotX(-angle, 1.0, 0.0);
		double nx_y = rotY(-angle, 1.0, 0.0);
		double ny_x = rotX(-angle, 0.0, 1.0);
		double ny_y = rotY(-angle, 0.0, 1.0);
		double x0 = rotX(-angle, -width / 2.0, -height / 2.0) + width / 2.0;
		double y0 = rotY(-angle, -width / 2.0, -height / 2.0) + height / 2.0;
		for (int y = 0; y < height; y++) {
			double x1 = x0;
			double y1 = y0;
			for (int x = 0; x < width; x++) {
				int xx = (int) x1;
				int yy = (int) y1;
				int col = 0;
				if (xx < 0 || xx >= width || yy < 0 || yy >= height) col = 0xffff00ff;
				else
					col = pixels[xx + yy * width];
				result[x + y * width] = col;
				x1 += nx_x;
				y1 += nx_y;
			}
			x0 += ny_x;
			y0 += ny_y;
		}
		return result;
	}

	private static double rotX(double angle, double x, double y) {
		double cos = Math.cos(angle - Math.PI / 2);
		double sin = Math.sin(angle - Math.PI / 2);
		return x * cos + y * -sin;
	}

	private static double rotY(double angle, double x, double y) {
		double cos = Math.cos(angle - Math.PI / 2);
		double sin = Math.sin(angle - Math.PI / 2);
		return x * sin + y * cos;
	}

	public static Sprite[] split(SpriteSheet sheet) {
		int amount = (sheet.getWidth() * sheet.getHeight()) / (sheet.SPRITE_WIDTH * sheet.SPRITE_HEIGHT);
		System.out.println(amount);
		Sprite[] sprites = new Sprite[amount];
		int current = 0;
		int[] pixels = new int[sheet.SPRITE_WIDTH * sheet.SPRITE_HEIGHT];

		for (int yp = 0; yp < sheet.getHeight() / sheet.SPRITE_HEIGHT; yp++) {
			System.out.println(yp);
			// 0 to 5
			for (int xp = 0; xp < sheet.getWidth() / sheet.SPRITE_WIDTH; xp++) {
				// 0 to 12
				for (int y = 0; y < sheet.SPRITE_HEIGHT; y++) {
					for (int x = 0; x < sheet.SPRITE_WIDTH; x++) {
						int xo = x + xp * sheet.SPRITE_WIDTH;
						int yo = y + yp * sheet.SPRITE_HEIGHT;

						pixels[x + y * sheet.SPRITE_WIDTH] = sheet.getPixels()[xo + yo * sheet.getWidth()];
					}
				}

				sprites[current++] = new Sprite(pixels, sheet.SPRITE_WIDTH, sheet.SPRITE_HEIGHT);
			}
		}

		return sprites;
	}

	public Sprite(int[] pixels, int size) {
		SIZE = width = height = size;
		this.pixels = pixels;
	}

	private void setColor(int color) {
		for (int i = 0; i < width * height; i++) {
			pixels[i] = color;
		}
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	private void load() {
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				pixels[x + y * width] = sheet.pixels[(x + this.x) + (y + this.y) * sheet.SPRITE_WIDTH];
			}
		}
	}

}
