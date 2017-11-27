package listeners;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
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

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

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
	private Point textLocation;
	private String textInput;
	private Color windowColor;
	private Point imageLocation;

	public PanelMouseListener(PaintWindow pw) {
		this.myWindow = pw;
		this.points = new ArrayList<Point>();
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		switch (this.myWindow.getToolType()) {
		case 0: // Brush
			this.points.add(new Point(e.getX(), e.getY()));
			if (this.points.size() > 0) {
				int layerNumber = this.myWindow.getCurrentLayer();
				BufferedImage img = this.myWindow.getDoodlePanel().getDisplayLayers().get(layerNumber);
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
		case 1: // Eraser
			this.points.add(new Point(e.getX(), e.getY()));
			if (this.points.size() > 0) {
				int layerNumber = this.myWindow.getCurrentLayer();
				BufferedImage img = this.myWindow.getDoodlePanel().getDisplayLayers().get(layerNumber);
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
			
		case 2:	// Draw oval
			setEndPoint(e.getX(), e.getY());
			
			// get the preview layer
			BufferedImage previewLayer = this.myWindow.getDoodlePanel().getPreviewLayer();
			Graphics2D g = previewLayer.createGraphics();
			
			// clear it
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g.setComposite(AlphaComposite.Src);
			g.setStroke(new BasicStroke(this.myWindow.getStrokeSize()));
			g.setColor(new Color(0,0,0,0));
			g.fillRect(0, 0, previewLayer.getWidth(), previewLayer.getHeight());
			
			// draw the oval and repaint
			g.setColor(this.myWindow.getColor());
			g.drawOval(Math.min(this.x1,this.x2), Math.min(this.y1,this.y2), Math.abs(this.x1-this.x2), Math.abs(this.y1-this.y2));
			this.myWindow.getDoodlePanel().repaint();
			break;
	
		case 3: // Draw rectangle	
			setEndPoint(e.getX(), e.getY());
			
			// get the preview layer
			BufferedImage previewLayer1 = this.myWindow.getDoodlePanel().getPreviewLayer();
			Graphics2D g1 = previewLayer1.createGraphics();
			
			// clear it
			g1.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g1.setComposite(AlphaComposite.Src);
			g1.setStroke(new BasicStroke(this.myWindow.getStrokeSize()));
			g1.setColor(new Color(0,0,0,0));
			g1.fillRect(0, 0, previewLayer1.getWidth(), previewLayer1.getHeight());
			
			// draw the rect and repaint
			g1.setColor(this.myWindow.getColor());
			g1.drawRect(Math.min(this.x1,this.x2), Math.min(this.y1,this.y2), Math.abs(this.x1-this.x2), Math.abs(this.y1-this.y2));
			this.myWindow.getDoodlePanel().repaint();
			break;
			
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		switch (this.myWindow.getToolType()) {
		case 4: // Bucket
			System.out.println(this.myWindow.getColor().hashCode());
			BucketInstruction bucketInstr = new BucketInstruction(e.getX(), e.getY(), this.myWindow.getColor().hashCode(),
					this.myWindow.getCurrentLayer(), this.myWindow.getClientId());
			this.myWindow.getDoodlePanel().executeInstruction(bucketInstr);
			this.myWindow.sendInstruction(bucketInstr);
			break;
			
		case 5: // Text
			int layerNumber = this.myWindow.getCurrentLayer();
			BufferedImage img = this.myWindow.getDoodlePanel().getDisplayLayers().get(layerNumber);
			Graphics2D g = img.createGraphics();
			this.textLocation = new Point(e.getX(), e.getY());
			try {
			this.textInput = (String) JOptionPane.showInputDialog(this.myWindow,
                                                                  "Enter text to print on doodle:",
                                                                  "Text tool",
                                                                  JOptionPane.PLAIN_MESSAGE,
                                                                  null, null, null);
			
			
		    g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);	
		    g.setColor(this.myWindow.getColor());
		    g.setFont(new Font("Monaco", Font.PLAIN, this.myWindow.getFontSize()));
		    	g.drawString(textInput, textLocation.x, textLocation.y);
		    	TextBoxInstruction textBoxInstr = new TextBoxInstruction(g.getColor(), g.getFont(), this.textInput,
		    	                                                         this.myWindow.getCurrentLayer(), this.textLocation, this.myWindow.getClientId());
			this.myWindow.sendInstruction(textBoxInstr);
			this.myWindow.getDoodlePanel().addInstruction(textBoxInstr);
			System.out.println("SENT INSTR:");
			System.out.println(textBoxInstr);
		    g.dispose();
		    this.myWindow.getDoodlePanel().repaint();
		    } catch (NullPointerException er) {
				System.out.println("Dialog cancelled.");
			}
			break;
		
		case 7: // Upload picture
			BufferedImage bimg = this.myWindow.getDoodlePanel().getDisplayLayers().get(this.myWindow.getCurrentLayer());
			Graphics2D _img = bimg.createGraphics();
			this.imageLocation = new Point(e.getX(), e.getY());
			try 
			{
				BufferedImage originalImg = ImageIO.read(new File(this.myWindow.getChosenImagePath()));
				_img.drawImage(originalImg, this.imageLocation.x, this.imageLocation.y, null);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			_img.dispose();
			this.myWindow.getDoodlePanel().repaint();
			break;
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		switch (this.myWindow.getToolType()) {
		case 2: // Draw oval
			setStartPoint(e.getX(), e.getY());
			break;
		case 3: // Draw rectangle
			setStartPoint(e.getX(), e.getY());
			break;
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		switch (this.myWindow.getToolType()) {
		case 0: // Brush
			if (this.points.size() >= 1) {
				ArrayList<Point> pointsDeepCopy = new ArrayList<Point>();
				for (Point p : this.points) {
					pointsDeepCopy.add((Point) p.clone());
				}
				BrushInstruction brushInstr = new BrushInstruction(this.myWindow.getColor(),
						this.myWindow.getStrokeSize(), this.myWindow.getCurrentLayer(), pointsDeepCopy,
						this.myWindow.getClientId());
				this.myWindow.getDoodlePanel().addInstruction(brushInstr);
				this.myWindow.sendInstruction(brushInstr);
				System.out.println("SENT INSTR:");
				System.out.println(brushInstr);
			}
			this.points.clear();
			break;
		case 1: // Eraser
			ArrayList<Point> pointsDeepCopy = new ArrayList<Point>();
			for (Point p : this.points) {
				pointsDeepCopy.add((Point) p.clone());
			}
			if (this.points.size() >= 1) {
				EraserInstruction eraserInstr = new EraserInstruction(this.myWindow.getStrokeSize(),
						this.myWindow.getCurrentLayer(), pointsDeepCopy, this.myWindow.getClientId());
				this.myWindow.sendInstruction(eraserInstr);
				this.myWindow.getDoodlePanel().addInstruction(eraserInstr);
				System.out.println("SENT INSTR:");
				System.out.println(eraserInstr);
			}
			this.points.clear();
			break;
			
		case 2: // Draw oval
			this.myWindow.getDoodlePanel().clearPreviewLayer();
			
			setEndPoint(e.getX(), e.getY());
			int layerNumber_ = this.myWindow.getCurrentLayer();
			BufferedImage img_ = this.myWindow.getDoodlePanel().getDisplayLayers().get(layerNumber_);
			Graphics2D g_ = img_.createGraphics();
			
			g_.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g_.setColor(this.myWindow.getColor());
			g_.setStroke(new BasicStroke(this.myWindow.getStrokeSize()));
			CircleInstruction circle = new CircleInstruction(this.myWindow.getColor(), this.myWindow.getStrokeSize(), this.myWindow.getCurrentLayer(), this.x1, this.y1, this.x2, this.y2, this.myWindow.getClientId());
			this.myWindow.sendInstruction(circle);
			this.myWindow.getDoodlePanel().addInstruction(circle);
			System.out.println("SENT INSTR:");
			System.out.println(circle);
			
			g_.drawOval(Math.min(this.x1,this.x2), Math.min(this.y1,this.y2), Math.abs(this.x1-this.x2), Math.abs(this.y1-this.y2));
			this.myWindow.getDoodlePanel().repaint();
			g_.dispose();
			break;
			
		case 3: // Draw rectangle
			this.myWindow.getDoodlePanel().clearPreviewLayer();
			
			setEndPoint(e.getX(), e.getY());
			int layerNumber = this.myWindow.getCurrentLayer();
			BufferedImage img = this.myWindow.getDoodlePanel().getDisplayLayers().get(layerNumber);
			Graphics2D g = img.createGraphics();
			
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g.setColor(this.myWindow.getColor());
			g.setStroke(new BasicStroke(this.myWindow.getStrokeSize()));
			RectangleInstruction rect = new RectangleInstruction(this.myWindow.getColor(), this.myWindow.getStrokeSize(), this.myWindow.getCurrentLayer(), this.x1, this.y1, this.x2, this.y2, this.myWindow.getClientId());
			this.myWindow.sendInstruction(rect);
			this.myWindow.getDoodlePanel().addInstruction(rect);
			System.out.println("SENT INSTR:");
			System.out.println(rect);
			
			g.drawRect(Math.min(this.x1,this.x2), Math.min(this.y1,this.y2), Math.abs(this.x1-this.x2), Math.abs(this.y1-this.y2));
			this.myWindow.getDoodlePanel().repaint();
			g.dispose();
			break;
		
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
