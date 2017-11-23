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
import model.BucketInstruction;
import model.CircleInstruction;
import model.EraserInstruction;
import model.RectangleInstruction;
import model.TextBoxInstruction;

public class PanelMouseListener implements MouseListener, MouseMotionListener {

	private int x1, y1, x2, y2;
	private PaintWindow myWindow;
	private ArrayList<Point> points;
	private Color windowColor;

	public PanelMouseListener(PaintWindow pw) {
		this.myWindow = pw;
		this.points = new ArrayList<Point>();
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		switch (this.myWindow.getToolType()) {
		case 0:
			this.points.add(new Point(e.getX(), e.getY()));
			if (this.points.size() > 0) {
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
			this.points.add(new Point(e.getX(), e.getY()));
			if (this.points.size() > 0) {
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
			break;
			
		case 3:
			this.points.add(new Point(e.getX(), e.getY()));
			if (this.points.size() > 0) {
				int layerNumber = this.myWindow.getCurrentLayer();
				BufferedImage img = this.myWindow.getDoodlePanel().getLayers().get(layerNumber);
				Graphics2D g = img.createGraphics();
				
			    g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);	
			    g.setColor(this.myWindow.getColor());
				g.setStroke(new BasicStroke(this.myWindow.getStrokeSize())); //?
			    g.drawString("HELLO?!?!?!!??!?", this.points.get(0).x, this.points.get(0).y);
			    g.dispose();
			    this.myWindow.getDoodlePanel().repaint();
			}
			break;
			
		case 5:	
			setEndPoint(e.getX(), e.getY());
			break;
		case 6:	
			setEndPoint(e.getX(), e.getY());

			
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		switch (this.myWindow.getToolType()) {
		case 2:
			System.out.println(this.myWindow.getColor().hashCode());
			BucketInstruction bucketInstr = new BucketInstruction(e.getX(), e.getY(), this.myWindow.getColor().hashCode(),
					this.myWindow.getCurrentLayer(), this.myWindow.getClientId());
			this.myWindow.getDoodlePanel().executeInstruction(bucketInstr);
			this.myWindow.sendInstruction(bucketInstr);
			break;
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		switch (this.myWindow.getToolType()) {
		case 5:
			setStartPoint(e.getX(), e.getY());
			break;
		case 6:
			setStartPoint(e.getX(), e.getY());
			break;
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		switch (this.myWindow.getToolType()) {
		case 0:
			if (this.points.size() >= 1) {
				BrushInstruction brushInstr = new BrushInstruction(this.myWindow.getColor(),
						this.myWindow.getStrokeSize(), this.myWindow.getCurrentLayer(), this.points,
						this.myWindow.getClientId());
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
		case 3:
			TextBoxInstruction textBoxInstr = new TextBoxInstruction(this.myWindow.getCurrentLayer(), 
					this.points, this.myWindow.getClientId());
			this.myWindow.sendInstruction(textBoxInstr);
			System.out.println("SENT INSTR:");
			System.out.println(textBoxInstr);
			this.points.clear();
			break;	
		case 5:
			setEndPoint(e.getX(), e.getY());
			int layerNumber = this.myWindow.getCurrentLayer();
			BufferedImage img = this.myWindow.getDoodlePanel().getLayers().get(layerNumber);
			Graphics2D g = img.createGraphics();
			
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g.setColor(this.myWindow.getColor());
			g.setStroke(new BasicStroke(this.myWindow.getStrokeSize()));
			RectangleInstruction rect = new RectangleInstruction(this.myWindow.getColor(), this.myWindow.getStrokeSize(), this.myWindow.getCurrentLayer(), this.x1, this.y1, this.x2, this.y2, this.myWindow.getClientId());
			this.myWindow.sendInstruction(rect);
			System.out.println("SENT INSTR:");
			System.out.println(rect);
			
			g.drawRect(Math.min(this.x1,this.x2), Math.min(this.y1,this.y2), Math.abs(this.x1-this.x2), Math.abs(this.y1-this.y2));
			this.myWindow.getDoodlePanel().repaint();
			g.dispose();
			break;
		case 6:
			setEndPoint(e.getX(), e.getY());
			int layerNumber_ = this.myWindow.getCurrentLayer();
			BufferedImage img_ = this.myWindow.getDoodlePanel().getLayers().get(layerNumber_);
			Graphics2D g_ = img_.createGraphics();
			
			g_.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g_.setColor(this.myWindow.getColor());
			g_.setStroke(new BasicStroke(this.myWindow.getStrokeSize()));
			CircleInstruction circle = new CircleInstruction(this.myWindow.getColor(), this.myWindow.getStrokeSize(), this.myWindow.getCurrentLayer(), this.x1, this.y1, this.x2, this.y2, this.myWindow.getClientId());
			this.myWindow.sendInstruction(circle);
			System.out.println("SENT INSTR:");
			System.out.println(circle);
			
			g_.drawOval(Math.min(this.x1,this.x2), Math.min(this.y1,this.y2), Math.abs(this.x1-this.x2), Math.abs(this.y1-this.y2));
			this.myWindow.getDoodlePanel().repaint();
			g_.dispose();
			
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
	
	public void setStartPoint(int x, int y) {
        this.x1 = x;
        this.y1 = y;
    }
	
	public void setEndPoint(int x, int y) { 
		this.x2 = x;
        this.y2 = y;
    }
	

}
