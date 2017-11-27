package model;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class LoadImageInstruction extends Instruction {
	private int layer;
	private BufferedImage image;
	
	public LoadImageInstruction(int _layer, BufferedImage _image, int _clientId) {
		this.layer = _layer;
		this.image = _image;
		super.clientId = _clientId;
	}

	@Override
	public void execute(ArrayList<BufferedImage> layers) {
		BufferedImage img = layers.get(this.layer);
		Graphics2D g = img.createGraphics();
		
		g.drawImage(img, 100, 100, null);
		
	}
	
}