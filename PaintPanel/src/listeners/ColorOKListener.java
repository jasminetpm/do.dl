package listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JColorChooser;

import gui.PaintWindow;

public class ColorOKListener implements ActionListener {
	private PaintWindow myWindow;
	private JColorChooser myColorChooser;
	
	public ColorOKListener (PaintWindow pw, JColorChooser jcc)
	{
		this.myWindow = pw;
		this.myColorChooser = jcc;
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		this.myWindow.setColor(this.myColorChooser.getColor()); // returns new color
		this.myWindow.setColorChooserIcon();
	}

}
