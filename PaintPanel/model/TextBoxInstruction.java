package model;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class TextBoxInstruction extends Instruction {

	private int layer;
	private ArrayList<Point> pointList;

	public TextBoxInstruction(int _layer, ArrayList<Point> _pointList, int _clientId) {
		this.layer = _layer;
		this.pointList = _pointList;
		super.clientId = _clientId;
	}

	@Override
	public void execute(ArrayList<BufferedImage> layers) {
		BufferedImage img = layers.get(this.layer);
		Graphics2D g = img.createGraphics();

	    g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
	    g.drawString("HELLOOOOOOOOOOOOOO", this.pointList.get(0).x, this.pointList.get(0).y);
	    g.dispose();
	}
	
	@Override
	public String toString() {
		return ("TextBoxInstruction(layer: " + this.layer + ", \npointList: "
				+ this.pointList + "\nclientId: " + super.clientId + "\n)");
	}

}
