package gui;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

import listeners.CommentButtonListener;

public class CommentEntryPane extends JPanel {
	private Dimension WINDOW_SIZE = new Dimension(640, 50);
	private JButton sendButton;
	private JTextField textEntryField;
	private PaintWindow pw;
	
	public CommentEntryPane(PaintWindow _pw) {
		this.pw = _pw;
		this.sendButton = new JButton("Comment");
		this.sendButton.addActionListener(new CommentButtonListener(this.pw));
		this.textEntryField = new JTextField(40);
		
		this.setLayout(new FlowLayout());
		this.add(this.textEntryField);
		this.add(this.sendButton);
	}
	
	public String getText() {
		return this.textEntryField.getText();
	}
}
