package listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import gui.PaintWindow;

/**
 * Listener for the cancel button on the color picker dialog.
 * 
 *
 */
public class ColorCancelListener implements ActionListener {
	private PaintWindow myWindow;
	
	public ColorCancelListener (PaintWindow pw)
	{
		this.myWindow = pw;
	}

	@Override
	/**
	 * sets color and updates color chooser button icon.
	 */
	public void actionPerformed(ActionEvent e) 
	{
		this.myWindow.setColor(this.myWindow.getColor()); // returns current color
		this.myWindow.setColorChooserIcon();
	}

}