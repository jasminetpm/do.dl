package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JPanel;

import listeners.PanelMouseListener;
import model.Instruction;

public class DoodlePanel extends JPanel {
	
	private PaintWindow myWindow;
	private PanelMouseListener pml;
	private ArrayList<BufferedImage> layers;
	
	public DoodlePanel(PaintWindow pw)
	{
		this.myWindow = pw;
		this.pml = new PanelMouseListener(this.myWindow);
		// add 3 layers
		this.layers = new ArrayList<BufferedImage>();
		for (int i = 0; i < 3; i++) 
		{
			BufferedImage img = new BufferedImage(650, 540, BufferedImage.TYPE_INT_ARGB);
			this.layers.add(img);
		}
		
		this.setBackground(Color.WHITE);
		this.addMouseListener(pml);
		this.addMouseMotionListener(pml);
	}
	
	public ArrayList<BufferedImage> getLayers() {
		return this.layers;
	}
	
	public void executeInstruction(Instruction instr) {
		instr.execute(layers);
		this.repaint();
	}
	
	@Override
	public void paintComponent(Graphics g) {
		for (BufferedImage img : this.layers) {
			g.drawImage(img, 0, 0, null);
		}
	}

}
