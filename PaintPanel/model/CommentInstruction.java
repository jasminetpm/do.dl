package model;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class CommentInstruction extends Instruction {
	private Color color;
	private int lineWidth;
	private int layer;
	private int x1;
	private int y1;
	private int x2;
	private int y2;
	private int index;
	private String comment;
	
	public CommentInstruction(Color _color, int _lineWidth, int _layer, int _x1, int _y1, int _x2, int _y2,
			int _clientId, int _index, String _comment) {
		this.color = _color;
		this.lineWidth = _lineWidth;
		this.layer = _layer;
		this.x1 = _x1;
		this.y1 = _y1;
		this.x2 = _x2;
		this.y2 = _y2;
		this.index = _index;
		this.comment = _comment;
		
		super.clientId = _clientId;
	}
	
	public String getCommentText() {
		return this.comment;
	}
	
	public void setCommentText(String _comment) {
		this.comment = _comment;
	}
	
	public int getIndex() {
		return this.index;
	}

	@Override
	public void execute(ArrayList<BufferedImage> layers) {
		BufferedImage img = layers.get(this.layer);
		Graphics2D g = img.createGraphics();

		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setStroke(new BasicStroke(this.lineWidth));
		g.setColor(new Color(this.color.getRed(),this.color.getGreen(),this.color.getBlue(),100));
		g.fillOval(Math.min(this.x1, this.x2), Math.min(this.y1, this.y2), Math.abs(this.x1 - this.x2),
				Math.abs(this.y1 - this.y2));

		g.setColor(new Color(this.color.getRed(),this.color.getGreen(),this.color.getBlue(),255));
		g.drawOval(Math.min(this.x1, this.x2), Math.min(this.y1, this.y2), Math.abs(this.x1 - this.x2),
				Math.abs(this.y1 - this.y2));
		g.dispose();
	}
}
