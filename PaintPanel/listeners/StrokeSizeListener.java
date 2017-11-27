package listeners;

import javax.swing.JSpinner;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import gui.PaintWindow;

public class StrokeSizeListener implements ChangeListener {
	private PaintWindow pw;
	
	/**
	 * Constructs an instance of the listener in the given PaintWindow.
	 * @param _pw the current PaintWindow
	 */
	public StrokeSizeListener(PaintWindow _pw) {
		this.pw = _pw;
	}

	@Override
	/**
	 * Updates the stroke size to the one chosen when the value in the corresponding JSpinner is changed.
	 */
	public void stateChanged(ChangeEvent e) 
	{
		JSpinner source = (JSpinner) e.getSource();
		int size = (int) source.getValue();
		this.pw.setStrokeSize(size);
	}

}
