package listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import gui.PaintWindow;

public class ColorPickerListener implements ActionListener {
	private PaintWindow myWindow;
	
	/**
	 * Constructs an instance of the listener in the given PaintWindow.
	 * @param pw the PaintWindow in which the listener is to be constructed
	 */
	public ColorPickerListener(PaintWindow pw)
	{
		this.myWindow = pw;
	}
	
	@Override
	/**
	 * Shows the color chooser dialog when the corresponding button is pressed.
	 */
	public void actionPerformed(ActionEvent e) 
	{
		this.myWindow.viewColorChooser();
	}
}
