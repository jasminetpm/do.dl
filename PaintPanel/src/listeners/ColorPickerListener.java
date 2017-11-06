package listeners;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JColorChooser;

import gui.PaintWindow;

public class ColorPickerListener implements ActionListener {
	private PaintWindow myWindow;
	
	public ColorPickerListener(PaintWindow pw)
	{
		this.myWindow = pw;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		this.myWindow.setColor(JColorChooser.showDialog(null, 
	               			"Pick your colour", 
	               			this.myWindow.getColor()));
		this.myWindow.setColorChooserIcon();
	}
}
