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
	
	public LayerSelectorListener(PaintWindow _pw) 
	{
		this.pw = _pw;
	}
	
	@Override
	public void stateChanged(ChangeEvent e)
	{
		JSpinner source = (JSpinner) e.getSource();
		int layer = (int) source.getValue();
		this.layer = layer - 1; // To correct for layer values starting from 0
		System.out.println("Switched to layer: " + this.layer);
		this.pw.setCurrentLayer(this.layer);
	}


}
