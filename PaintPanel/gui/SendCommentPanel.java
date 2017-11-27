package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

import chatUI.MainWindow;
import listeners.CommentListener;
import listeners.EnterKeyListener;
import listeners.SendButtonListener;

public class SendCommentPanel extends JPanel{
	private PaintWindow pw;
	private JButton sendButton;
	public  JTextArea jta;
	public JScrollPane paneScroll;
	
	public SendCommentPanel(PaintWindow pw) {
		// TODO Auto-generated constructor stub
		this.pw = pw;
		this.setLayout(new BorderLayout());
		this.sendButton = new JButton("Comment");
		this.jta = new JTextArea();
		this.jta.setFont(new Font("Monaco", Font.PLAIN, 12));
		this.jta.setLineWrap(true);
		this.paneScroll = new JScrollPane(jta);
		
		this.paneScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED); 
		this.paneScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

		this.jta.requestFocus();
		this.sendButton.addActionListener(new CommentListener(this.pw, this.jta));
		//this.jta.addKeyListener(new CommentKeyListener(this.pw, this.jta));
//		this.jta.getDocument().addDocumentListener(new InputMessageListener(this, this.jta));
		this.jta.setBackground(Color.WHITE);
		this.add(this.jta, BorderLayout.CENTER);
		this.add(this.sendButton, BorderLayout.LINE_END);
	}
	
	public JTextArea getJTA()
	{
		return this.jta;
	}
	
	public String getTextJTA()
	{
		return this.jta.getText();
	}

}
