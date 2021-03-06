package com.danrok.rain.graphics.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import com.danrok.rain.util.Vector2i;

public class UILabel extends UIComponent {

	public String text;
	private Font font;
	public boolean dropShadow = false;
	public int dropShadowOffset = 1;

	public UILabel(Vector2i position, String text) {
		super(position);
		font = new Font("Verdana", Font.PLAIN, 32);
		this.text = text;
		color = new Color(0xff00ff);
	}

	public UILabel setFont(Font font) {
		this.font = font;
		return this;
	}

	public void render(Graphics g) {
		if (dropShadow) {
			g.setFont(font);
			g.setColor(Color.BLACK);
			g.drawString(text, position.x + offset.x + 1, position.y + offset.y + dropShadowOffset);
		}
		g.setColor(color);
		g.setFont(font);
		g.drawString(text, position.x + offset.x, position.y + offset.y);
		//font.render(position.x + offset.x, position.y + offset.y, -9, 0, text, screen);	
	}

}
