package listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import gui.PaintWindow;

/**
 * Listener to open color chooser window.
 * 
 *
 */
public class ColorPickerListener implements ActionListener {
	private PaintWindow myWindow;
	/**
	 * Constructor for colorpicker listener.
	 * @param pw current PaintWindow
	 */
	public ColorPickerListener(PaintWindow pw)
	{
		this.myWindow = pw;
	}
	
	@Override
	/**
	 * Opens color chooser dialog when button pressed.
	 */
	public void actionPerformed(ActionEvent e) 
	{
		this.myWindow.viewColorChooser();
	}
}
