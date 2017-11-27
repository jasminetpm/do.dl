package listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import gui.PaintWindow;

public class ColorCancelListener implements ActionListener {
	private PaintWindow myWindow;
	
	/**
	 * Constructs an instance of the listener in the given PaintWindow.
	 * @param pw the PaintWindow in which the listener is constructed
	 */
	public ColorCancelListener (PaintWindow pw)
	{
		this.myWindow = pw;
	}

	@Override
	/**
	 * If the CANCEL button is pressed, the previously picked color gets set as the current color.
	 */
	public void actionPerformed(ActionEvent e) 
	{
		this.myWindow.setColor(this.myWindow.getColor()); // returns current color
		this.myWindow.setColorChooserIcon();
	}

}