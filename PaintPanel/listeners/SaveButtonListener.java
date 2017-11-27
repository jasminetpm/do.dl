package listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import gui.PaintWindow;

public class SaveButtonListener implements ActionListener {
	private PaintWindow myWindow;
	
	/**
	 * Constructs a listener in a specified PaintWindow.
	 * @param pw the PaintWindow in which the listener should be instantiated
	 */
	public SaveButtonListener(PaintWindow pw) {
		this.myWindow = pw;
	}

	@Override
	/**
	 * Shows the file chooser dialog when the button is pressed.
	 */
	public void actionPerformed(ActionEvent e) {
		this.myWindow.viewFileChooser();
		
	}
	

}
