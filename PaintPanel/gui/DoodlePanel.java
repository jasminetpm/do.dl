package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.swing.JPanel;

import listeners.PanelMouseListener;
import model.CanvasState;
import model.CommentInstruction;
import model.Instruction;

public class DoodlePanel extends JPanel {
	
	private PaintWindow myWindow;
	private PanelMouseListener pml;
	private ArrayList<BufferedImage> baseLayers;
	private ArrayList<BufferedImage> displayLayers;
	private LinkedList<Instruction> instructionLog;
	private BufferedImage previewLayer;
	private Dimension WINDOW_SIZE = new Dimension(650, 540);
	
	public DoodlePanel(PaintWindow pw)
	{
		this.myWindow = pw;
		this.pml = new PanelMouseListener(this.myWindow);
		// add 4 layers
		this.baseLayers = new ArrayList<BufferedImage>();
		this.displayLayers = new ArrayList<BufferedImage>();
		for (int i = 0; i < 5; i++) 
		{
			this.baseLayers.add(new BufferedImage(650, 540, BufferedImage.TYPE_INT_ARGB));
			this.displayLayers.add(new BufferedImage(650, 540, BufferedImage.TYPE_INT_ARGB));
		}
		this.instructionLog = new LinkedList<Instruction>();
		this.previewLayer = new BufferedImage(650, 540, BufferedImage.TYPE_INT_ARGB);
		this.setBackground(Color.WHITE);
		this.setPreferredSize(this.WINDOW_SIZE);
		this.addMouseListener(pml);
		this.addMouseMotionListener(pml);
	}
	
	public ArrayList<BufferedImage> getBaseLayers() {
		return this.baseLayers;
	}
	
	public ArrayList<BufferedImage> getDisplayLayers() {
		return this.displayLayers;
	}
	
	public BufferedImage getPreviewLayer() {
		return this.previewLayer;
	}
	
	public void clearPreviewLayer() {
		this.previewLayer = new BufferedImage(650, 540, BufferedImage.TYPE_INT_ARGB);
		this.repaint();
	}
	
	// use this one for adding an instruction to the queue after real-time drawing
	public void addInstruction(Instruction instr) {
		Instruction poppedInstr;
		
		this.instructionLog.add(instr);
		if (this.instructionLog.size() > 20) {
			poppedInstr = this.instructionLog.removeFirst();
			poppedInstr.execute(baseLayers);
		}
	}
	
	public void updateState(CanvasState currentState) {
		ArrayList<BufferedImage> _displayLayers = new ArrayList<BufferedImage>();
		
		// get the information from the state object
		this.instructionLog = currentState.getLog();
		this.baseLayers = currentState.getLayers();

		// copy the base layers
		for (BufferedImage img : this.baseLayers) {
			_displayLayers.add(this.deepCopy(img));
		}
		
		// reconstruct the display layers by executing the instructions on them
		for (Instruction instr : this.instructionLog) {
			System.out.println(instr);
			instr.execute(_displayLayers);
		}
		
		// set display layers to new display layers, and redraw
		this.displayLayers = _displayLayers;
		this.repaint();
	}
	
	public void undo() {
		ArrayList<BufferedImage> _displayLayers = new ArrayList<BufferedImage>();
		BufferedImage test;
		
		// only undo if there are instructions in the log
		if (this.instructionLog.size() > 0) {
			// copy the base layers
			for (BufferedImage img : this.baseLayers) {
				_displayLayers.add(this.deepCopy(img));
			}
			
			// remove the last stored instruction
			this.instructionLog.removeLast();
			
			// reconstruct the display layers by executing the instructions on them
			for (Instruction instr : this.instructionLog) {
				System.out.println(instr);
				instr.execute(_displayLayers);
			}
			
			// set display layers to new display layers, and redraw
			this.displayLayers = _displayLayers;
			this.repaint();
		} else {
			System.out.println("Instruction log is empty!");
		}
	}
	
	public void executeInstruction(Instruction instr) {
		Instruction poppedInstr;
		
		// we only execute instructions on display layers
		instr.execute(this.displayLayers);
		
		// unless the queue size > 20, then we start modifying the base layers
		if (instr instanceof CommentInstruction) {
			this.myWindow.getCommentList().add((CommentInstruction) instr);
		} else {
			this.instructionLog.add(instr);
		}
		
		if (this.instructionLog.size() > 20) {
			poppedInstr = this.instructionLog.removeFirst();
			poppedInstr.execute(this.baseLayers);
		}
		
		this.repaint();
	}
	
	public BufferedImage mergeARGBLayers(ArrayList<BufferedImage> layers)
	{
		BufferedImage mergedImage = new BufferedImage(650, 540, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = mergedImage.createGraphics();
		g.setBackground(Color.WHITE); // Forces background to be white
		g.clearRect(0, 0, 650, 540);
		for (int i = 0; i < layers.size(); i++)
		{
			g.drawImage(layers.get(i), 0, 0, null);
		}
		return mergedImage;
	}
	
	public BufferedImage mergeRGBLayers(ArrayList<BufferedImage> layers)
	{
		BufferedImage mergedImage = new BufferedImage(650, 540, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = mergedImage.createGraphics();
		g.setColor(Color.WHITE); // Forces background to be white
		g.fillRect(0, 0, 650, 540);
		for (int i = 0; i < layers.size(); i++)
		{
			g.drawImage(layers.get(i), 0, 0, null);
		}
		return mergedImage;
	}
	
	// taken from StackOverflow as a method to deep copy an image
	private BufferedImage deepCopy(BufferedImage bi) {
		 ColorModel cm = bi.getColorModel();
		 boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
		 WritableRaster raster = bi.copyData(null);
		 return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
	}
	
	public void repaintComments() {
		this.displayLayers.set(4, new BufferedImage(650, 540, BufferedImage.TYPE_INT_ARGB));
		
		for (CommentInstruction comment : this.myWindow.getCommentList()) {
			comment.execute(this.displayLayers);
		}
		
		this.repaint();
	}
	
	@Override
	public void paintComponent(Graphics g) {
		for (int i=0; i < 5; i++) {
			g.drawImage(this.displayLayers.get(i), 0, 0, null);
			if (i == this.myWindow.getCurrentLayer()) {
				g.drawImage(this.previewLayer, 0, 0, null);
			}
		}
	}

}
