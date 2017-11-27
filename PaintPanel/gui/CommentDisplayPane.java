package gui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import model.CommentInstruction;

public class CommentDisplayPane extends JPanel {
	private Dimension COMPONENT_SIZE = new Dimension(640, 50);
	private Dimension SCROLLPANE_SIZE = new Dimension(615, 40);
	private JTextArea textArea;
	private JScrollPane scrollPane;
	private JPanel wrapper;
	private PaintWindow pw;
	
	public CommentDisplayPane(PaintWindow _pw) {
		this.setPreferredSize(COMPONENT_SIZE);
		this.pw = _pw;
	
		this.wrapper = new JPanel();
		this.wrapper.setLayout(new BoxLayout(this.wrapper, BoxLayout.Y_AXIS));
		this.scrollPane = new JScrollPane(this.wrapper);
		this.scrollPane.setPreferredSize(SCROLLPANE_SIZE);
		this.scrollPane.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		this.add(this.scrollPane);
	}
	
	public void repopulateComments() {
		this.wrapper.removeAll();
		for (CommentInstruction comment : this.pw.getCommentList()) {
			this.wrapper.add(new Comment(comment.getIndex(), comment.getCommentText()));
		}
		this.revalidate();
		this.repaint();
	}
	
	public void addComment(int index, String comment) {
		System.out.println("Add comment: is it working?");
		this.wrapper.add(new Comment(index, comment));
		this.revalidate();
		this.repaint();
	}
}
