package model;
/*
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class CommentOrderInstruction extends Instruction {
	private Color color;
	private int lineWidth;
	private int layer;
	private int x1;
	private int y1;
	private int x2;
	private int y2;
	private int order;
	
	public CommentOrderInstruction(int order, int _x1, int _y1, int _x2, int _y2,  int _clientId) {
		this.color = Color.RED;
		this.lineWidth = 10;
		this.layer = 4;
		this.x1 = _x1;
		this.y1 = _y1;
		this.x2 = _x2;
		this.y2 = _y2;
		super.clientId = _clientId;
		this.order = order;
	}
	

	@Override
	public void execute(ArrayList<BufferedImage> layers) {
		BufferedImage img = layers.get(this.layer);
		Graphics2D g = img.createGraphics();
		
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		char[] charArray = new char[] {Integer.toString(order).charAt(0)};
		g.setFont(new Font("Monaco", Font.PLAIN, 20));
		g.drawChars(charArray, 0, 1, this.x1,  this.y1);
		g.dispose();
		
	}
}
*/