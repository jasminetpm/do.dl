package listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import gui.PaintWindow;
import model.CommentInstruction;
import model.RemoveCommentInstruction;

/**
 * Listener for delete comment button
 *
 */
public class DeleteCommentListener implements ActionListener {
	private PaintWindow pw;
	private int index;

	/**
	 * Constructs a listener for the corresponding button
	 * @param _pw current PaintWindow
	 * @param _index the corresponding comment index
	 */
	public DeleteCommentListener(PaintWindow _pw, int _index) {
		this.pw = _pw;
		this.index = _index;
	}
	
	@Override
	/**
	 * Removes comment when clicked.
	 */
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		this.pw.sendInstruction(new RemoveCommentInstruction(this.index));
	}

}
