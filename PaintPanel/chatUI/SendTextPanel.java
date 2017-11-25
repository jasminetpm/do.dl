package chatUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.sun.xml.internal.txw2.Document;

import chatUI.MainWindow;
import listeners.EnterKeyListener;
import listeners.InputMessageListener;
import listeners.SendButtonListener;

public class SendTextPanel extends JPanel 
{
	private MainWindow mw;
	private JButton sendButton;
	public  JTextArea jta;
	public JScrollPane paneScroll;
	private ImageIcon sendIcon = new ImageIcon("PaintPanel/imagesource/ic_send_black_24dp_1x.png");

	public SendTextPanel(MainWindow mw) {
		// TODO Auto-generated constructor stub
		this.mw = mw;
		this.setLayout(new BorderLayout());
		this.sendButton = new JButton(sendIcon);
		this.jta = new JTextArea();
		this.jta.setFont(new Font("Monaco", Font.PLAIN, 12));
		this.jta.setLineWrap(true);
		this.paneScroll = new JScrollPane(jta);
		
		this.paneScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED); 
		this.paneScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

		this.jta.requestFocus();
		this.sendButton.addActionListener(new SendButtonListener(this.mw, this.jta));
		this.jta.addKeyListener(new EnterKeyListener(this.mw, this.jta));
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