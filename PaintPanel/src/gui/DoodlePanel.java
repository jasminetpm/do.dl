package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import listeners.BrushMouseListener;
import model.Instruction;

public class DoodlePanel extends JPanel {
	
	private PaintWindow myWindow;
	private BrushMouseListener bml;
	private BufferedImage img;
	
	public DoodlePanel(PaintWindow pw)
	{
		this.myWindow = pw;
		this.bml = new BrushMouseListener(this.myWindow);
		this.img = new BufferedImage(650, 540, BufferedImage.TYPE_INT_ARGB);
		
		this.setBackground(Color.WHITE);
		this.addMouseListener(bml);
		this.addMouseMotionListener(bml);
	}
	
	public void executeInstruction(Instruction instr) {
		Graphics2D g = this.img.createGraphics();
		instr.execute(g);
		this.repaint();
	}
	
	@Override
	public void paintComponent(Graphics g) {
		g.drawImage(this.img, 0, 0, null);
	}

}
