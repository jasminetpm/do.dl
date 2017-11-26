package listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import gui.PaintWindow;

public class SaveButtonListener implements ActionListener {
	private PaintWindow myWindow;
	
	public SaveButtonListener(PaintWindow pw) {
		this.myWindow = pw;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		this.myWindow.viewFileChooser();
		
	}
	

}
