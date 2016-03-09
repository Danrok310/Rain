package com.danrok.rain.entity.mob;

import java.awt.Font;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.danrok.rain.Game;
import com.danrok.rain.entity.projectile.Projectile;
import com.danrok.rain.entity.projectile.SorcererProjectile;
import com.danrok.rain.graphics.AnimatedSprite;
import com.danrok.rain.graphics.Screen;
import com.danrok.rain.graphics.Sprite;
import com.danrok.rain.graphics.SpriteSheet;
import com.danrok.rain.graphics.ui.UIActionListener;
import com.danrok.rain.graphics.ui.UIButton;
import com.danrok.rain.graphics.ui.UIButtonListener;
import com.danrok.rain.graphics.ui.UILabel;
import com.danrok.rain.graphics.ui.UIManager;
import com.danrok.rain.graphics.ui.UIPanel;
import com.danrok.rain.graphics.ui.UIProgressBar;
import com.danrok.rain.input.Keyboard;
import com.danrok.rain.input.Mouse;
import com.danrok.rain.util.Vector2i;

public class Player extends Mob {

	private String name;

	private Keyboard input;
	private Sprite sprite;
	private boolean walking = false;

	private AnimatedSprite down = new AnimatedSprite(SpriteSheet.player_down, 32, 32, 3);
	private AnimatedSprite up = new AnimatedSprite(SpriteSheet.player_up, 32, 32, 3);
	private AnimatedSprite left = new AnimatedSprite(SpriteSheet.player_left, 32, 32, 3);
	private AnimatedSprite right = new AnimatedSprite(SpriteSheet.player_right, 32, 32, 3);

	private AnimatedSprite animSprite = down;

	Projectile p;
	private int fireRate = 0;

	private UIManager ui;
	private UIProgressBar uiHealthBar;
	private UIProgressBar uiManaBar;
	private UIButton button;

	@Deprecated
	public Player(String name, Keyboard input) {
		this.name = name;
		this.input = input;
		sprite = Sprite.player_down;
	}

	public Player(String name, int x, int y, Keyboard input) {
		this.name = name;
		this.x = x;
		this.y = y;
		this.input = input;
		sprite = Sprite.player_down;
		fireRate = SorcererProjectile.FIRE_RATE;

		ui = Game.getUIManager();
		UIPanel panel = (UIPanel) new UIPanel(new Vector2i((300 - 80) * 3, 0), new Vector2i(80 * 3, 168 * 3)).setColor(0x2b2b2b);
		ui.addPanel(panel);
		UILabel nameLabel = new UILabel(new Vector2i(40, 330), name);
		nameLabel.setColor(0x6aa506);
		nameLabel.setFont(new Font("Verdana", Font.PLAIN, 24));
		nameLabel.dropShadow = true;
		panel.addComponent(nameLabel);

		uiHealthBar = new UIProgressBar(new Vector2i(10, 340), new Vector2i(80 * 3 - 20, 20));
		uiHealthBar.setColor(0x6a6a6a);
		uiHealthBar.setForegroundColor(0xee3030);
		panel.addComponent(uiHealthBar);

		UILabel hpLabel = new UILabel(new Vector2i(uiHealthBar.position).add(new Vector2i(2, 16)), "HP");
		hpLabel.setColor(0xffffff);
		hpLabel.setFont(new Font("Verdana", Font.PLAIN, 18));
		panel.addComponent(hpLabel);

		uiManaBar = new UIProgressBar(new Vector2i(10, 362), new Vector2i(80 * 3 - 20, 20));
		uiManaBar.setColor(0x6a6a6a);
		uiManaBar.setForegroundColor(0x3066ee);
		panel.addComponent(uiManaBar);

		UILabel mpLabel = new UILabel(new Vector2i(uiManaBar.position).add(new Vector2i(2, 16)), "MP");
		mpLabel.setColor(0xffffff);
		mpLabel.setFont(new Font("Verdana", Font.PLAIN, 18));
		panel.addComponent(mpLabel);
		
		
		// Player default attributes
		health = 100;
		mana = 80;

		/*button = new UIButton(new Vector2i(120, 20), new Vector2i(80, 30), new UIActionListener() {
			public void perform() {
				//System.exit(0);
				System.out.println("Action Performed!");
			}

		});
					/even older/
					 * button.setButtonListener(new UIButtonListener(){
						public void pressed(UIButton button){
							super.pressed(button);
							button.performAction();
							button.ignoreNextPress();
						}
					});
					/
		button.setText("Exit");
		panel.addComponent(button);*/

		try {
			UIButton imageButton = new UIButton(new Vector2i(20, 20), ImageIO.read(new File("res/textures/sorcerer.png")), new UIActionListener() {
				public void perform() {
					System.out.println("Action Performed!");
				}
			});
			panel.addComponent(imageButton);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public String getName() {
		return name;
	}

	int time = 0;
	public void update() {
		if (walking) animSprite.update();
		else
			animSprite.setFrame(1);
		if (fireRate > 0) fireRate--;
		double xa = 0, ya = 0;
		double speed = 1.5;
		if (input.up) {
			ya -= speed;
			animSprite = up;
		} else if (input.down) {
			ya += speed;
			animSprite = down;
		}
		if (input.left) {
			xa -= speed;
			animSprite = left;
		} else if (input.right) {
			xa += speed;
			animSprite = right;
		}
		if (xa != 0 || ya != 0) {
			move(xa, ya);
			walking = true;
		} else {
			walking = false;
		}
		clear();
		updateShooting();
		uiHealthBar.setProgress(health / 100.0);
		uiManaBar.setProgress(time++ % 100 / 100.0);
	}

	private void clear() {
		for (int i = 0; i < level.getProjectiles().size(); i++) {
			Projectile p = level.getProjectiles().get(i);
			if (p.isRemoved()) level.getProjectiles().remove(i);
		}
	}

	private void updateShooting() {
		if (Mouse.getButton() == 1 && fireRate <= 0) {
			double dx = Mouse.getX() - Game.getWindowWidth() / 2;
			double dy = Mouse.getY() - Game.getWindowHeight() / 2;
			double dir = Math.atan2(dy, dx);
			shoot(x, y, dir);
			fireRate = SorcererProjectile.FIRE_RATE;
		}

	}

	public void render(Screen screen) {
		sprite = animSprite.getSprite();
		screen.renderMob((int) (x - 16), (int) (y - 16), sprite);

	}

}
