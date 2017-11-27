package gui;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JOptionPane;

import listeners.CommentButtonListener;

public class CommentDialog extends JPanel {
	private PaintWindow pw;
	
	public CommentDialog(PaintWindow _pw) 
	{
		this.pw = _pw;
	}
	
	public String getText() {
		return JOptionPane.showInputDialog("Enter comment here:");
	}
}
