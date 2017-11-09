package model;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.LinkedList;
import java.awt.image.DataBufferInt;
import org.apache.commons.collections4.ListUtils;


public class BucketInstruction extends Instruction {
	private int oldColor;
	private int newColor;
	private int cursorX;
	private int cursorY;
	private int layer;
	private int[][] pixels = new int[540][650];
	
	public BucketInstruction(int x, int y, int _newColor,  int _layer, int _clientId) {
		super.clientId = _clientId;
		this.newColor = _newColor;
		this.cursorX = x;
		this.cursorY = y;
		this.layer = _layer;
	}
	
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
					
					if (this.pixels[r-1][c] == this.oldColor) {
						queue.add(new Point(c,(r-1)));
					}
					if (this.pixels[r+1][c] == this.oldColor) {
						queue.add(new Point(c,(r+1)));
					}
					if (this.pixels[r][c-1] == this.oldColor) {
						queue.add(new Point((c-1),r));
					}
					if (this.pixels[r][c+1] == this.oldColor) {
						queue.add(new Point((c+1),r));
					}
				}
			}
		}
		
		return;
	}
	
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
			traverse(this.cursorY, this.cursorX);
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
