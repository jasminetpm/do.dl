package chatUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
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

/**
 * the sendTextPanel where user inputs text and gets sent
 * @author Coco
 * @version 1.0 Nov 2017
 */
public class SendTextPanel extends JPanel 
{
	/**
	 * the MainWindow where it locates
	 */
	private MainWindow mw;
	/**
	 * the sendButtor which upon clicking will send the message to the server
	 */
	private JButton sendButton;
	/**
	 * the jtextarea where the user types in his chats 
	 */
	public  JTextArea jta;
	/**
	 * enables scrolling
	 */
	public JScrollPane paneScroll;
	/**
	 * the icon to make the UI look nicer
	 */
	private ImageIcon sendIcon = new ImageIcon("PaintPanel/imagesource/ic_send_black_24dp_1x.png");

	/**
	 * constructor for SendTextPanel
	 * @param mw: the MainWindow on which SendTextPanel is initiated
	 */
	public SendTextPanel(MainWindow mw) {
		// TODO Auto-generated constructor stub
		this.mw = mw;
		this.setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
		this.sendButton = new JButton(sendIcon);
		this.jta = new JTextArea();
		this.jta.setFont(new Font("Monaco", Font.PLAIN, 12));
		this.jta.setLineWrap(true);
//		this.paneScroll = new JScrollPane(jta);
//		
//		this.paneScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED); 
//		this.paneScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

		this.jta.requestFocus();
		this.sendButton.addActionListener(new SendButtonListener(this.mw, this.jta));
		this.jta.addKeyListener(new EnterKeyListener(this.mw, this.jta));
//		this.jta.getDocument().addDocumentListener(new InputMessageListener(this, this.jta));
		this.jta.setBackground(Color.WHITE);
		this.add(this.jta);
		this.add(this.sendButton);
	}
	
	/**
	 * getter for JTextArea
	 * @return the JTextArea associated with the SendTextPanel
	 */
	public JTextArea getJTA()
	{
		return this.jta;
	}
	
	/**
	 * return the text stored in the JTextArea
	 * @return the message needing to be sent
	 */
	public String getTextJTA()
	{
		return this.jta.getText();
	}
	

}