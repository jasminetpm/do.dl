package listeners;

import javax.swing.JSpinner;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import gui.PaintWindow;

public class StrokeSizeListener implements ChangeListener {
	private PaintWindow pw;
	
	public StrokeSizeListener(PaintWindow _pw) {
		this.pw = _pw;
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		// TODO Auto-generated method stub
		JSpinner source = (JSpinner) e.getSource();
		int size = (int) source.getValue();
		this.pw.setStrokeSize(size);
	}

}
