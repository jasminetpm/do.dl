package listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import gui.PaintWindow;
import model.UndoInstruction;

public class ToolSelectorListener implements ActionListener {
	private int toolId;
	private PaintWindow pw;
	
	public ToolSelectorListener(int _toolId, PaintWindow _pw) {
		this.toolId = _toolId;
		this.pw = _pw;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("Switched to type: " + this.toolId);
		this.pw.getDoodlePanel().clearPreviewLayer();
		this.pw.clearCommentInstruction();
		if (this.toolId < 8) {
			this.pw.setToolType(this.toolId);
		} else if (this.toolId == 9) {
			// undo
			this.pw.getDoodlePanel().undo();
			this.pw.sendInstruction(new UndoInstruction(this.pw.getClientId()));
		}
	}

}
