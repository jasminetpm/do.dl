package listeners;

import javax.swing.JSpinner;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import gui.PaintWindow;

/**
 * Listener for font size setter
 *
 */
public class FontSizeListener implements ChangeListener {
	
	private PaintWindow pw;

	/**
	 * Constructs listener for font size
	 * @param _pw current PaintWindow
	 */
	public FontSizeListener(PaintWindow _pw) {
		this.pw = _pw;
	}
	
	@Override
	/**
	 * Changes font size to corresponding size when values changed.
	 */
	public void stateChanged(ChangeEvent e) 
	{
		JSpinner source = (JSpinner) e.getSource();
		int fontSize = (int) source.getValue();
		this.pw.setFontSize(fontSize);
	}
	
}
