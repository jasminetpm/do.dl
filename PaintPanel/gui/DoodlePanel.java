package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.swing.JPanel;

import listeners.PanelMouseListener;
import model.Instruction;

public class DoodlePanel extends JPanel {
	
	private PaintWindow myWindow;
	private PanelMouseListener pml;
	private ArrayList<BufferedImage> baseLayers;
	private ArrayList<BufferedImage> displayLayers;
	private LinkedList<Instruction> instructionLog;
	
	public DoodlePanel(PaintWindow pw)
	{
		this.myWindow = pw;
		this.pml = new PanelMouseListener(this.myWindow);
		// add 4 layers
		this.baseLayers = new ArrayList<BufferedImage>();
		this.displayLayers = new ArrayList<BufferedImage>();
		for (int i = 0; i < 4; i++) 
		{
			this.baseLayers.add(new BufferedImage(650, 540, BufferedImage.TYPE_INT_ARGB));
			this.displayLayers.add(new BufferedImage(650, 540, BufferedImage.TYPE_INT_ARGB));
		}
		this.instructionLog = new LinkedList<Instruction>();
		this.setBackground(Color.WHITE);
		this.addMouseListener(pml);
		this.addMouseMotionListener(pml);
	}
	
	public ArrayList<BufferedImage> getBaseLayers() {
		return this.baseLayers;
	}
	
	public ArrayList<BufferedImage> getDisplayLayers() {
		return this.displayLayers;
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
	
	public void undo() {
		ArrayList<BufferedImage> _displayLayers = new ArrayList<BufferedImage>();
		BufferedImage test;
		
		// only undo if there are instructions in the log
		if (this.instructionLog.size() > 0) {
			// copy the base layers
			System.out.println("Hello 1");
			for (BufferedImage img : this.baseLayers) {
				_displayLayers.add(this.deepCopy(img));
			}
			
			// remove the last stored instruction
			System.out.println("Hello 2");
			this.instructionLog.removeLast();
			
			// reconstruct the display layers by executing the instructions on them
			for (Instruction instr : this.instructionLog) {
				System.out.println(instr);
				instr.execute(_displayLayers);
			}
			
			// set display layers to new display layers, and redraw
			System.out.println("Hello 4");
			this.displayLayers = _displayLayers;
			System.out.println("Hello 5");
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
		this.instructionLog.add(instr);
		if (this.instructionLog.size() > 20) {
			poppedInstr = this.instructionLog.removeFirst();
			poppedInstr.execute(baseLayers);
		}
		
		this.repaint();
	}
	
	// taken from StackOverflow as a method to deep copy an image
	private BufferedImage deepCopy(BufferedImage bi) {
		 ColorModel cm = bi.getColorModel();
		 boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
		 WritableRaster raster = bi.copyData(null);
		 return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		for (BufferedImage img : this.displayLayers) {
			g.drawImage(img, 0, 0, null);
		}
	}

}
