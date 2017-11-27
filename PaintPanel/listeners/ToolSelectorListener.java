package listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import gui.PaintWindow;
import model.UndoInstruction;

public class ToolSelectorListener implements ActionListener {
	private int toolId;
	private PaintWindow pw;
	
	/**
	 * Constructs an instance of the listener in the given PaintWindow, for a specific tool.
	 * @param _toolId the ID of the tool for the listener to be attached to
	 * @param _pw the current PaintWindow
	 */
	public ToolSelectorListener(int _toolId, PaintWindow _pw) 
	{
		this.toolId = _toolId;
		this.pw = _pw;
	}
	
	@Override
	/**
	 * Sets the tool type to the corresponding ID when the corresponding button is pressed.
	 */
	public void actionPerformed(ActionEvent e) 
	{
		System.out.println("Switched to type: " + this.toolId);
		if (this.toolId < 8) {
			this.pw.setToolType(this.toolId);
		} else if (this.toolId == 9) {
			// undo
			this.pw.getDoodlePanel().undo();
			this.pw.sendInstruction(new UndoInstruction(this.pw.getClientId()));
		}
	}

}
