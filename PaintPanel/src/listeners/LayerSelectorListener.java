package listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import gui.PaintWindow;

public class LayerSelectorListener implements ActionListener {
	private int layer;
	private PaintWindow pw;
	
	public LayerSelectorListener(int _layer, PaintWindow _pw) {
		this.layer = _layer;
		this.pw = _pw;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		System.out.println("Switched to layer: " + this.layer);
		this.pw.setCurrentLayer(this.layer);
	}

}
