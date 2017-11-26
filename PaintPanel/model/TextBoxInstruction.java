package model;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class TextBoxInstruction extends Instruction {

	private int layer;
	private Color color;
	private Font font;
	private String textInput;
	private Point textLocation;

	public TextBoxInstruction(Color _color, Font _font, String _textInput, int _layer, Point _textLocation, int _clientId) {
		this.color = _color;
		this.font = _font;
		this.textInput = _textInput;
		this.layer = _layer;
		this.textLocation = _textLocation;
		super.clientId = _clientId;
	}

	@Override
	public void execute(ArrayList<BufferedImage> layers) {
		BufferedImage img = layers.get(this.layer);
		Graphics2D g = img.createGraphics();
		
	    g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
	    g.setColor(this.color);
	    g.setFont(this.font);
	    g.drawString(this.textInput, this.textLocation.x, this.textLocation.y);
	    g.dispose();
	}
	
	@Override
	public String toString() {
		return ("TextBoxInstruction(color: " + this.color + "\nfont: " + this.font +
				"\ntextInput: " + this.textInput + "\nlayer: " + this.layer + ", \nlocation: "
				+ this.textLocation + "\nclientId: " + super.clientId + "\n)");
	}

}
