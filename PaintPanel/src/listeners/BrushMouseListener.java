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

public class BrushMouseListener implements MouseListener, MouseMotionListener {

	private PaintWindow myWindow;
	private ArrayList<Point> points;
	private Color windowColor;
	
	public BrushMouseListener (PaintWindow pw)
	{
		this.myWindow = pw;
		this.points = new ArrayList<Point>();
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		this.points.add(new Point(e.getX(),e.getY()));
//		myWindow.getJPanel().addPoint(e.getX(), e.getY(), myWindow.getColor());
//		myWindow.getJPanel().repaint();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		this.windowColor = this.myWindow.getColor();
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		BrushInstruction instr = new BrushInstruction(this.myWindow.getColor(), this.myWindow.getStrokeSize(), 0, this.points);
		this.myWindow.sendInstruction(instr);
		System.out.println("SENT INSTR:");
		System.out.println(instr);
		this.points.clear();
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
