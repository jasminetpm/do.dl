package listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import gui.PaintWindow;

public class CommentButtonListener implements ActionListener {
	private PaintWindow pw;
	
	public CommentButtonListener(PaintWindow _pw) {
		this.pw = _pw;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		this.pw.sendCommentInstruction();
	}

}
