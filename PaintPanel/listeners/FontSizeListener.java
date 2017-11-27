package listeners;

import javax.swing.JSpinner;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import gui.PaintWindow;

public class FontSizeListener implements ChangeListener {
	
	private PaintWindow pw;

	/**
	 * Constructs an instance of the listener in the given PaintWindow.
	 * @param _pw the PaintWindow in which the listener is to be constructed
	 */
	public FontSizeListener(PaintWindow _pw) {
		this.pw = _pw;
	}
	
	@Override
	/**
	 * Updates the font size when the JSpinner value is changed.
	 */
	public void stateChanged(ChangeEvent e) 
	{
		JSpinner source = (JSpinner) e.getSource();
		int fontSize = (int) source.getValue();
		this.pw.setFontSize(fontSize);
	}
	
}
