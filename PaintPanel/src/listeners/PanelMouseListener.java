package listeners;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
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
	
	public PanelMouseListener (PaintWindow pw)
	{
		this.myWindow = pw;
		this.points = new ArrayList<Point>();
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		this.points.add(new Point(e.getX(),e.getY()));
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
				BrushInstruction brushInstr = new BrushInstruction(this.myWindow.getColor(), this.myWindow.getStrokeSize(), this.myWindow.getCurrentLayer(), this.points);
				this.myWindow.sendInstruction(brushInstr);
				System.out.println("SENT INSTR:");
				System.out.println(brushInstr);
				this.points.clear();
				break;
			case 1:
				EraserInstruction eraserInstr = new EraserInstruction(this.myWindow.getStrokeSize(), this.myWindow.getCurrentLayer(), this.points);
				this.myWindow.sendInstruction(eraserInstr);
				System.out.println("SENT INSTR:");
				System.out.println(eraserInstr);
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
