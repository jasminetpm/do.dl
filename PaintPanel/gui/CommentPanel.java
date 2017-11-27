package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.text.DefaultCaret;

import chatUI.MainWindow;

public class CommentPanel extends JPanel 
{
	private PaintWindow pw;
	private JScrollPane jsp;
	private JTextArea text;
	private int counter;

	public CommentPanel(PaintWindow pw) 
	{
		this.pw = pw;
		this.counter = 0;
		this.text = new JTextArea();
		this.text.setFont(new Font("Monaco", Font.PLAIN, 12));
		this.text.setEditable(false);
		// Shifts scroll to bottom of page whenever new message received
		DefaultCaret caret = (DefaultCaret) this.text.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		// Places JTextArea in JScrollPane
		this.jsp = new JScrollPane(text);
		//this.jsp.setPreferredSize(new Dimension(260, 470));
		this.text.setLineWrap(true);
		this.jsp.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		this.jsp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		this.add(this.jsp);
		
	}
	
	public void textAdded(String message)
	{
		this.counter++;
		message = "Comment " + this.counter +": " + "<" + this.pw.getMw().getUserName() + ">: " + message;
		String originalText = this.text.getText();
		this.text.setText(originalText + '\n' + '\n' + message); 
		this.text.repaint();
		this.pw.getACP().repaint();
	}
	
	public void textReceived(String message)
	{
		String originalText = this.text.getText();
		this.text.setText(originalText + '\n' + '\n' + message); 
		this.text.repaint();
		this.pw.getACP().repaint();
	}
	
	public JTextArea getJTA()
	{
		return this.text;
		
	}
	
	public int getCounter()
	{
		return this.counter;
	}

}
