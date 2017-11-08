package listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import gui.PaintWindow;

public class ColorCancelListener implements ActionListener {
	private PaintWindow myWindow;
	
	public ColorCancelListener (PaintWindow pw)
	{
		this.myWindow = pw;
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		this.myWindow.setColor(this.myWindow.getColor()); // returns current color
		this.myWindow.setColorChooserIcon();
	}

}