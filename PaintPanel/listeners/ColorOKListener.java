package listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JColorChooser;

import gui.PaintWindow;

/**
 * 
 * Listener to color chooser OK button.
 *
 */
public class ColorOKListener implements ActionListener {
	private PaintWindow myWindow;
	private JColorChooser myColorChooser;
	
	/**
	 * Constructor for ColorOKListener
	 * @param pw current PaintWindow
	 * @param jcc JColorChooser
	 */
	public ColorOKListener (PaintWindow pw, JColorChooser jcc)
	{
		this.myWindow = pw;
		this.myColorChooser = jcc;
	}

	@Override
	/**
	 * Sets color to chosen color.
	 */
	public void actionPerformed(ActionEvent e) 
	{
		this.myWindow.setColor(this.myColorChooser.getColor()); // returns new color
		this.myWindow.setColorChooserIcon();
	}

}
