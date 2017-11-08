package chatUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import com.sun.xml.internal.txw2.Document;

import chatUI.MainWindow;
import listeners.SendButtonListener;

public class SendTextPanel extends JPanel 
{
	private MainWindow mw;
	private JButton sendButton;
	private JTextArea jta;

	public SendTextPanel(MainWindow mw) {
		// TODO Auto-generated constructor stub
		this.mw = mw;
		this.setLayout(new BorderLayout());
		this.sendButton = new JButton("Send");
		this.sendButton.addActionListener(new SendButtonListener(this.mw));
		this.jta = new JTextArea(7, 15);
		this.jta.setBackground(Color.WHITE);
		this.add(this.sendButton, BorderLayout.EAST);
		this.add(this.jta, BorderLayout.WEST);
	}
	
	public JTextArea getJTA()
	{
		return this.jta;
	}

}