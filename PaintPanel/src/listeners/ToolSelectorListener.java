package listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import gui.PaintWindow;

public class ToolSelectorListener implements ActionListener {
	private int toolId;
	private PaintWindow pw;
	
	public ToolSelectorListener(int _toolId, PaintWindow _pw) {
		this.toolId = _toolId;
		this.pw = _pw;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		System.out.println("Switched to type: " + this.toolId);
		this.pw.setToolType(this.toolId);
	}

}
