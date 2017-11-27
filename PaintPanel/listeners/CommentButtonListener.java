package listeners;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import chatUI.SendTextPanel;
import gui.PaintWindow;
import model.CircleInstruction;

public class CommentButtonListener implements ActionListener {
	private PaintWindow pw;
	private PanelMouseListener circle;
	

	public CommentButtonListener(PaintWindow pw)
	{
		this.pw = pw;
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		this.circle = new PanelMouseListener(this.pw);
		
		this.pw.incrementCommentCount();
		this.pw.setPrintFlag(1);
		this.pw.setCircleFLag(1);
	}
}




