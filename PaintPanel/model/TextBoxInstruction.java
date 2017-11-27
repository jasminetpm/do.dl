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

	/**
	 * Constructs an instance of the instructions for the text function.
	 * @param _color the color of the text
	 * @param _font the font of the text
	 * @param _textInput the text to be printed
	 * @param _layer the layer on which the text should be printed
	 * @param _textLocation the coordinates where the text should be printed
	 * @param _clientId the clientId from which the instructions are sent
	 */
	public TextBoxInstruction(Color _color, Font _font, String _textInput, int _layer, Point _textLocation, int _clientId) 
	{
		this.color = _color;
		this.font = _font;
		this.textInput = _textInput;
		this.layer = _layer;
		this.textLocation = _textLocation;
		super.clientId = _clientId;
	}

	@Override
	/**
	 * The instructions to be executed on the given layer. 
	 */
	public void execute(ArrayList<BufferedImage> layers) 
	{
		BufferedImage img = layers.get(this.layer);
		Graphics2D g = img.createGraphics();
		
	    g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
	    g.setColor(this.color);
	    g.setFont(this.font);
	    g.drawString(this.textInput, this.textLocation.x, this.textLocation.y);
	    g.dispose();
	}
	
	@Override
	/**
	 * The given instructions as a String.
	 */
	public String toString() {
		return ("TextBoxInstruction(color: " + this.color + "\nfont: " + this.font +
				"\ntextInput: " + this.textInput + "\nlayer: " + this.layer + ", \nlocation: "
				+ this.textLocation + "\nclientId: " + super.clientId + "\n)");
	}

}
