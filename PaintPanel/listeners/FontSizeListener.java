package listeners;

import javax.swing.JSpinner;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import gui.PaintWindow;

public class FontSizeListener implements ChangeListener {
	
	private PaintWindow pw;

	public FontSizeListener(PaintWindow _pw) {
		this.pw = _pw;
	}
	
	@Override
	public void stateChanged(ChangeEvent e) 
	{
		JSpinner source = (JSpinner) e.getSource();
		int fontSize = (int) source.getValue();
		this.pw.setFontSize(fontSize);
	}
	
}
