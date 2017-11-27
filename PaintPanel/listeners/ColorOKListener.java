package listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JColorChooser;

import gui.PaintWindow;

public class ColorOKListener implements ActionListener {
	private PaintWindow myWindow;
	private JColorChooser myColorChooser;
	
	/**
	 * Constructs an instance of the listener in the given PaintWindow and JColorChooser.
	 * @param pw the current PaintWindow
	 * @param jcc the current color chooser dialog
	 */
	public ColorOKListener (PaintWindow pw, JColorChooser jcc)
	{
		this.myWindow = pw;
		this.myColorChooser = jcc;
	}

	@Override
	/**
	 * Sets the current color to the one chosen when the OK button is pressed.
	 */
	public void actionPerformed(ActionEvent e) 
	{
		this.myWindow.setColor(this.myColorChooser.getColor()); // returns new color
		this.myWindow.setColorChooserIcon();
	}

}
