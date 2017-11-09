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
	public  JTextArea jta;

	public SendTextPanel(MainWindow mw) {
		// TODO Auto-generated constructor stub
		this.mw = mw;
		this.setLayout(new BorderLayout());
		this.sendButton = new JButton("Send");
		this.jta = new JTextArea();
		this.jta.requestFocus();
		this.sendButton.addActionListener(new SendButtonListener(this.mw, this.jta));
		this.jta.setBackground(Color.WHITE);
		this.add(this.sendButton, BorderLayout.LINE_END);
		this.add(this.jta, BorderLayout.CENTER);
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