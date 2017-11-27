package listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import gui.PaintWindow;
import model.CommentInstruction;
import model.RemoveCommentInstruction;

public class DeleteCommentListener implements ActionListener {
	private PaintWindow pw;
	private int index;

	public DeleteCommentListener(PaintWindow _pw, int _index) {
		this.pw = _pw;
		this.index = _index;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		this.pw.sendInstruction(new RemoveCommentInstruction(this.index));
	}

}
