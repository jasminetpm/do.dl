package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import listeners.DeleteCommentListener;
import model.CommentInstruction;

public class Comment extends JPanel {
	private PaintWindow pw;
	private int index;
	private String comment;
	private JPanel headerWrapper;
	private JLabel header;
	private JButton close;
	private JTextArea commentDisplay;
	private Dimension HEADER_SIZE = new Dimension(600, 20);
	
	public Comment(PaintWindow _pw, int _index, String _comment) {
		this.pw = _pw;
		this.index = _index;
		this.comment = _comment;
		
		this.setLayout(new BorderLayout());
		
		// set up header
		this.headerWrapper = new JPanel();
		this.headerWrapper.setLayout(new BorderLayout());
		this.header = new JLabel("Comment #" + this.index);
		this.close = new JButton("X");
		this.close.addActionListener(new DeleteCommentListener(this.pw, this.index));
		this.headerWrapper.add(this.header, BorderLayout.LINE_START);
		this.headerWrapper.add(this.close, BorderLayout.LINE_END);
		
		// set up comment display
		this.commentDisplay = new JTextArea(this.comment);
		this.commentDisplay.setEditable(false);
		
		// put it together
		this.add(this.headerWrapper, BorderLayout.PAGE_START);
		this.add(this.commentDisplay, BorderLayout.CENTER);
	}
}
