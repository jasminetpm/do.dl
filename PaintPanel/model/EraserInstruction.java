package model;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.geom.GeneralPath;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * A class which represents a path made by the eraser tool
 * @author Daniel Lok
 */
public class EraserInstruction extends Instruction {
	private int lineWidth;
	private int layer;
	private ArrayList<Point> pointList;

	/**
	 * Class constructor
	 * @param _lineWidth the path's line width
	 * @param _layer the layer which we want to erase on
	 * @param _pointList the point list which the path is comprised of
	 * @param _clientId the client's ID (provided by the server)
	 */
	public EraserInstruction(int _lineWidth, int _layer, ArrayList<Point> _pointList, int _clientId) {
		this.lineWidth = _lineWidth;
		this.layer = _layer;
		this.pointList = _pointList;
		super.clientId = _clientId;
	}

	/**
	 * Draws the path on the appropriate layer.
	 * @param layers the displayLayers field of the DoodlePanel
	 */
	@Override
	public void execute(ArrayList<BufferedImage> layers) {
		BufferedImage img = layers.get(this.layer);
		Graphics2D g = img.createGraphics();

		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setComposite(AlphaComposite.Clear);
		g.setColor(new Color(255, 255, 255, 255));
		g.setStroke(new BasicStroke(this.lineWidth));
		GeneralPath path = new GeneralPath();

		path.moveTo(this.pointList.get(0).x, this.pointList.get(0).y);
		for (int i = 1; i < this.pointList.size(); i++) {
			path.lineTo(this.pointList.get(i).x, this.pointList.get(i).y);
		}

		g.draw(path);
		g.dispose();
	}
	
	@Override
	public String toString() {
		return ("EraserInstruction(lineWidth:" + this.lineWidth + ", \nlayer: " + this.layer + ", \npointList: "
				+ this.pointList + "\nclientId: " + super.clientId + "\n)");
	}
}
