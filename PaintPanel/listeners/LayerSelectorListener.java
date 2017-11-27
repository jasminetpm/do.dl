package listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JSpinner;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import gui.PaintWindow;

public class LayerSelectorListener implements ChangeListener {
	
	private int layer;
	private PaintWindow pw;
	
	/**
	 * Constructs an instance of the listener in the given PaintWindow.
	 * @param _pw the PaintWindow in which the listener is to be constructed
	 */
	public LayerSelectorListener(PaintWindow _pw) 
	{
		this.pw = _pw;
	}
	
	@Override
	/**
	 * Updates the layer selected when the value of the corresponding JSpinner is changed.
	 */
	public void stateChanged(ChangeEvent e)
	{
		JSpinner source = (JSpinner) e.getSource();
		int layer = (int) source.getValue();
		this.layer = layer - 1; // To correct for layer values starting from 0
		System.out.println("Switched to layer: " + this.layer);
		this.pw.setCurrentLayer(this.layer);
	}


}
