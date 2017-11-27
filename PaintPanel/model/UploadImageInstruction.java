package model;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class UploadImageInstruction extends Instruction {
	
	private int layer;
	private Point imageLocation;
	private BufferedImage img;
	
	
	public UploadImageInstruction(int _layer, Point _imageLocation, BufferedImage _img, int _clientId)
	{
		this.layer = _layer;
		this.imageLocation = _imageLocation;
		this.img = _img;
		super.clientId = _clientId;
	}
	
	@Override
	public void execute(ArrayList<BufferedImage> layers) {
		BufferedImage bimg = layers.get(this.layer);
		Graphics g = bimg.createGraphics();
		
		g.drawImage(this.img, this.imageLocation.x, this.imageLocation.y, null);
		g.dispose();
	}

	@Override
	public String toString() {
		return ("UploadImageInstruction(layer: " + this.layer + "\nimageLocation: " +
	            this.imageLocation + "\ncliendId: " + super.clientId + "\n");
	}
}
