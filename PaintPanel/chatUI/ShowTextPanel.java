package chatUI;

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

public class ShowTextPanel extends JPanel 
{
	private MainWindow mw;
	private JScrollPane jsp;
	private JTextArea text;

	public ShowTextPanel(MainWindow mw) 
	{
		this.mw = mw;
		this.text = new JTextArea();
		this.text.setFont(new Font("Monaco", Font.PLAIN, 12));
		this.text.setEditable(false);
		// Shifts scroll to bottom of page whenever new message received
		DefaultCaret caret = (DefaultCaret) this.text.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		// Places JTextArea in JScrollPane
		this.jsp = new JScrollPane(text);
		this.jsp.setPreferredSize(new Dimension(260, 570));
		this.text.setLineWrap(true);
		this.jsp.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		this.jsp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		this.add(this.jsp);
		
	}
	
	public void textAdded(String message)
	{
		message = "<" + this.mw.getUserName() + ">: " + message;
		String originalText = this.text.getText();
		this.text.setText(originalText + '\n' + '\n' + message); 
		this.text.repaint();
	}
	
	public void textReceived(String message)
	{
		String originalText = this.text.getText();
		this.text.setText(originalText + '\n' + '\n' + message); 
		this.text.repaint();
	}
	
	public JTextArea getJTA()
	{
		return this.text;
	}

}
