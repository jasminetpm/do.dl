package listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
		this.myWindow.viewColorChooser();
	}
}
