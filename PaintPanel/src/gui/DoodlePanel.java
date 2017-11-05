package gui;

import java.awt.Color;

import javax.swing.JPanel;

public class DoodlePanel extends JPanel {
	
	private PaintWindow myWindow;
	
	public DoodlePanel(PaintWindow pw)
	{
		this.myWindow = pw;
		this.setBackground(Color.WHITE);
	}

}
