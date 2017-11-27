package model;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.LinkedList;
import java.awt.image.DataBufferInt;

/**
 * A class which represents a fill made by the bucket tool
 * 
 * @author Daniel Lok
 */
public class BucketInstruction extends Instruction {
	private int oldColor;
	private int newColor;
	private int cursorX;
	private int cursorY;
	private int layer;
	private int[][] pixels = new int[540][650];
	
	/**
	 * Class constructor.
	 * 
	 * @param x
	 *            the x-coordinate of where the user clicked
	 * @param y
	 *            the y-coordinate of where the user clicked
	 * @param _newColor
	 *            the color of the fill
	 * @param _layer
	 *            the layer which the instruction was called on
	 * @param _clientId
	 *            the client's ID (provided by the server)
	 */
	public BucketInstruction(int x, int y, int _newColor,  int _layer, int _clientId) {
		super.clientId = _clientId;
		this.newColor = _newColor;
		this.cursorX = x;
		this.cursorY = y;
		this.layer = _layer;
	}
	
	/**
	 * Fills a bounded area with the BucketInstruction's newColor. Performs an
	 * iterative breadth-first search on a 2D-array of colors, and recolors them
	 * appropriately.
	 * 
	 * @param row
	 *            the x-coordinate of where the user clicked
	 * @param column
	 *            the y-coordinate of where the user clicked
	 */
	private void traverse(int row, int column) {
		LinkedList<Point> queue = new LinkedList<Point>();
		Point currentPoint;
		int r;
		int c;
		queue.add(new Point(column,row));
		
		while (queue.size() > 0) {
			currentPoint = queue.removeFirst();
			r = currentPoint.y;
			c = currentPoint.x;
			if (!(r <= 0 || r >= 539 || c <= 0 || c >= 649)) {
				if (this.pixels[r][c] == this.oldColor) {
					this.pixels[r][c] = this.newColor;
					
					for (int i = r-1; i < r+2; i++) {
						for (int j = c-1; j < c+2; j++) {
							if (this.pixels[i][j] == this.oldColor) {
								queue.add(new Point(j, i));
							}
						}
					}
				}
			}
		}
		
		return;
	}
	
	/**
	 * Performs the fill on the appropriate layer. This function converts the layer
	 * into a 2D array of colors, and then calls traverse() on the array. A new
	 * image is then created from this array.
	 * @param layers the displayLayer field of the DoodlePanel
	 */
	@Override
	public void execute(ArrayList<BufferedImage> layers) {
		
		BufferedImage newImg = new BufferedImage(650,540, BufferedImage.TYPE_INT_ARGB);
		BufferedImage img = layers.get(this.layer);
		Graphics2D g = img.createGraphics();
		int[] rawPixels;
		
		rawPixels = ((DataBufferInt) img.getRaster().getDataBuffer()).getData();
		for (int i=0; i < 540; i++) {
			for (int j=0; j < 650; j++) {
				this.pixels[i][j] = rawPixels[(650 * i) + j];
			}
		}
		this.oldColor = this.pixels[this.cursorY][this.cursorX];
		
		if (this.oldColor != this.newColor) {
			System.out.println("Recursing...");
			this.traverse(this.cursorY, this.cursorX);
			System.out.println("Done Recursing...");
			for (int i=0; i < 650; i++) {
				for (int j=0; j < 540; j++) {
					newImg.setRGB(i, j, pixels[j][i]);
				}
			}
			
			g.drawImage(newImg, 0, 0, null);
		}
		
		g.dispose();
	}

}
