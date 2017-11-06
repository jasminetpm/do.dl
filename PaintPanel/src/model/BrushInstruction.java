package model;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.geom.GeneralPath;
import java.util.ArrayList;

public class BrushInstruction extends Instruction {
	private Color color;
	private int lineWidth;
	private int layer;
	private ArrayList<Point> pointList;

	public BrushInstruction(Color _color, int _lineWidth, int _layer, ArrayList<Point> _pointList) {
		this.color = _color;
		this.lineWidth = _lineWidth;
		this.layer = _layer;
		this.pointList = _pointList;
	}
	
	public void execute(Graphics2D g) {
		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setColor(this.color);
		g.setStroke(new BasicStroke(this.lineWidth));
		GeneralPath path = new GeneralPath();
		
		path.moveTo(this.pointList.get(0).x, this.pointList.get(0).y);
		for (int i=1; i < this.pointList.size(); i++) {
			path.lineTo(this.pointList.get(i).x, this.pointList.get(i).y);
		}
		
		g.draw(path);
	}
	
	public Color getColor() {
		return this.color;
	}
	
	public int getLineWidth() {
		return this.lineWidth;
	}
	
	public int getLayer() {
		return this.layer;
	}
	
	public ArrayList<Point> getPointList() {
		return this.pointList;
	}
	
	@Override
	public String toString() {
		return ("BrushInstruction(\ncolor: " + this.color + ", \nlineWidth:" + this.lineWidth + ", \nlayer: " + this.layer + ", \npointList: " + this.pointList + "\n)");
	}
}
