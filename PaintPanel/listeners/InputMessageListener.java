package listeners;

import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import chatUI.SendTextPanel;
import gui.PaintWindow;

public class InputMessageListener implements DocumentListener {
	private SendTextPanel textPanel;
	private JTextArea messageArea;
	private static final int CHAT_ROW_LIMIT = 4;
	
	public InputMessageListener(SendTextPanel stp, JTextArea jta) 
	{
		this.textPanel = stp;
		this.messageArea = jta;
		
	}
	
	private void updateLineCount()
	{
		int lineCount = this.messageArea.getLineCount();
		if (lineCount <= CHAT_ROW_LIMIT)
		{
			this.messageArea.setRows(lineCount);
			this.textPanel.revalidate();
		}
	}

	@Override
	public void insertUpdate(DocumentEvent e) {
		updateLineCount();
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		updateLineCount();
	}

	@Override
	public void changedUpdate(DocumentEvent e) {
		updateLineCount();
	}

}
