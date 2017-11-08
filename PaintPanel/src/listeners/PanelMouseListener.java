package listeners;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.GeneralPath;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import gui.PaintWindow;
import model.BrushInstruction;
import model.EraserInstruction;

public class PanelMouseListener implements MouseListener, MouseMotionListener {

	private PaintWindow myWindow;
	private ArrayList<Point> points;
	private Color windowColor;

	public PanelMouseListener(PaintWindow pw) {
		this.myWindow = pw;
		this.points = new ArrayList<Point>();
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		this.points.add(new Point(e.getX(), e.getY()));
		switch (this.myWindow.getToolType()) 
		{
			case 0:
				if (this.points.size() > 0) 
				{
					int layerNumber = this.myWindow.getCurrentLayer();
					BufferedImage img = this.myWindow.getDoodlePanel().getLayers().get(layerNumber);
					Graphics2D g = img.createGraphics();
		
					g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
					g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
					g.setColor(this.myWindow.getColor());
					g.setStroke(new BasicStroke(this.myWindow.getStrokeSize()));
					GeneralPath path = new GeneralPath();
		
					path.moveTo(this.points.get(0).x, this.points.get(0).y);
					for (int i = 1; i < this.points.size(); i++) {
						path.lineTo(this.points.get(i).x, this.points.get(i).y);
					}
		
					g.draw(path);
					g.dispose();
					this.myWindow.getDoodlePanel().repaint();
				}
				break;
			case 1:
				if (this.points.size() > 0) 
				{
					int layerNumber = this.myWindow.getCurrentLayer();
					BufferedImage img = this.myWindow.getDoodlePanel().getLayers().get(layerNumber);
					Graphics2D g = img.createGraphics();
		
					g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
					g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
					g.setComposite(AlphaComposite.Clear);
					g.setColor(new Color(255, 255, 255, 255));
					g.setStroke(new BasicStroke(this.myWindow.getStrokeSize()));
					GeneralPath path = new GeneralPath();
		
					path.moveTo(this.points.get(0).x, this.points.get(0).y);
					for (int i = 1; i < this.points.size(); i++) {
						path.lineTo(this.points.get(i).x, this.points.get(i).y);
					}
		
					g.draw(path);
					g.dispose();
					this.myWindow.getDoodlePanel().repaint();
				}
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		this.windowColor = this.myWindow.getColor();
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		switch (this.myWindow.getToolType()) {
			case 0:
				if (this.points.size() >= 1) {
					BrushInstruction brushInstr = new BrushInstruction(this.myWindow.getColor(), this.myWindow.getStrokeSize(),
					this.myWindow.getCurrentLayer(), this.points, this.myWindow.getClientId());
					this.myWindow.sendInstruction(brushInstr);
					System.out.println("SENT INSTR:");
					System.out.println(brushInstr);
				}
				this.points.clear();
				break;
			case 1:
				if (this.points.size() >= 1) {
					EraserInstruction eraserInstr = new EraserInstruction(this.myWindow.getStrokeSize(),
					this.myWindow.getCurrentLayer(), this.points, this.myWindow.getClientId());
					this.myWindow.sendInstruction(eraserInstr);
					System.out.println("SENT INSTR:");
					System.out.println(eraserInstr);
				}
				this.points.clear();
				break;
		default:
		}

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}
