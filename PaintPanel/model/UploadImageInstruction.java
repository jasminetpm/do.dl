package model;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class UploadImageInstruction extends Instruction {
	
	private int layer;
	private Point imageLocation;
	private BufferedImage bimage;
	private ByteArrayInputStream is;
//	private OutputStream oout;
	
	
	public UploadImageInstruction(int _layer, Point _imageLocation, byte[] _img, int _clientId) throws IOException
	{
		this.layer = _layer;
		this.imageLocation = _imageLocation;
		this.is = new ByteArrayInputStream(_img);
		this.bimage = ImageIO.read(this.is);
		super.clientId = _clientId;
	}
	
	@Override
	public void execute(ArrayList<BufferedImage> layers) {
//		try {
//			this.bimage = ImageIO.read(is);
			System.out.println("HELLO I RECEIVED THE INSTRUCTION");
			BufferedImage bimg = layers.get(this.layer);
			Graphics g = bimg.createGraphics();		
			g.drawImage(this.bimage, this.imageLocation.x, this.imageLocation.y, null);
			g.dispose();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
	}


	@Override
	public String toString() {
		return ("UploadImageInstruction(layer: " + this.layer + "\nimageLocation: " +
	            this.imageLocation + "\nByteArray: " +"\ncliendId: " + super.clientId + "\n");
	}
}
