package com.danrok.rain;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;

import com.danrok.rain.entity.mob.Player;
import com.danrok.rain.graphics.Screen;
import com.danrok.rain.graphics.ui.UIManager;
import com.danrok.rain.input.Keyboard;
import com.danrok.rain.input.Mouse;
import com.danrok.rain.level.Level;
import com.danrok.rain.level.TileCoordinate;

public class Game extends Canvas implements Runnable {
	private static final long serialVersionUID = 1L;

	private static int width = 300 - 80;
	private static int height = (width + 80) / 16 * 9;
	private static int scale = 3;
	public static String title = "Rain";

	private Thread thread;
	private JFrame frame;
	private Keyboard key;
	private Level level;
	private Player player;
	private boolean running = false;

	private static UIManager uiManager;

	private Screen screen;

	private BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	private int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();

	public Game() {
		Dimension size = new Dimension(width * scale + 80 * 3, height * scale);
		setPreferredSize(size);

		screen = new Screen(width, height);
		uiManager = new UIManager();
		frame = new JFrame();
		key = new Keyboard();
		level = Level.spawn;
		TileCoordinate playerSpawn = new TileCoordinate(14, 15);
		player = new Player("Sparklespice", playerSpawn.x(), playerSpawn.y(), key);
		level.add(player);

		addKeyListener(key);

		Mouse mouse = new Mouse();
		addMouseListener(mouse);
		addMouseMotionListener(mouse);
	}

	public static int getWindowWidth() {
		return width * scale;
	}

	public static int getWindowHeight() {
		return height * scale;
	}

	public static UIManager getUIManager() {
		return uiManager;

	}

	public synchronized void start() {
		running = true;
		thread = new Thread(this, "Display");
		thread.start();
	}

	public synchronized void stop() {
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		long lastTime = System.nanoTime();
		long timer = System.currentTimeMillis();
		final double ns = 1000000000.0 / 60.0;
		double delta = 0;
		int frames = 0;
		int updates = 0;
		requestFocus();
		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while (delta >= 1) {
				update();
				updates++;
				delta--;
			}
			render();
			frames++;

			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				System.out.println(updates + "ups, " + frames + " fps");
				frame.setTitle(title + "  |  " + updates + "ups, " + frames + " fps");
				updates = 0;
				frames = 0;
			}
		}
		stop();
	}

	public void update() {
		key.update();
		level.update();
		uiManager.update();
	}

	public void render() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) { // We don't want to render as fast as the computer (during run)
			createBufferStrategy(3); // Using triple buffering
			return;
		}

		screen.clear();
		double xScroll = player.getX() - screen.width / 2;
		double yScroll = player.getY() - screen.height / 2;
		level.render((int) xScroll, (int) yScroll, screen);

		//screen.renderSheet(40,  40, SpriteSheet.player_down, false);

		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = screen.pixels[i];
		}

		Graphics g = bs.getDrawGraphics();
		g.setColor(new Color(0xff00ff));
		g.fillRect(0, 0, getWidth(), getHeight());
		//g.setColor(Color.BLACK);
		//g.fillRect(0, 0, getWidth(), getHeight());
		g.drawImage(image, 0, 0, width * scale, height * scale, null);
		uiManager.render(g);
		//g.setColor(Color.WHITE);
		//g.setFont(new java.awt.Font("Verdana", 0, 20));
		//g.drawString("Hello", 50, 50);

		//g.fillRect(Mouse.getX() - 32, Mouse.getY() - 32, 64 , 64);
		//if (Mouse.getButton() != -1) g.drawString("Button " + Mouse.getButton(), 80, 80);

		g.dispose(); // need to make sure we dispose the frame once we're finished rendering it
		bs.show(); // show's the next available buffer

	}

	public static void main(String[] args) {
		Game game = new Game(); // Created a new object of Game
		game.frame.setResizable(false);
		game.frame.setTitle("Rain");
		game.frame.add(game); // The reason we can add game to the frame is because Game is a sub-class of Canvas now by extending it
		game.frame.pack();
		game.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		game.frame.setLocationRelativeTo(null); // this centers the game
		game.frame.setVisible(true); // We have to make it visible manually
		game.start();
	}
}
